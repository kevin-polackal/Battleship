package cs3500.pa03.controller;

import cs3500.pa03.model.AbstractPlayer;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.GameState;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.ManualPlayerSalvoData;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.Validation;
import cs3500.pa03.view.View;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***The Controller for a BattleShip game*/
public class BattleShipController implements Controller {
  private final View view;
  private GameState manualBoard;
  private GameState aiBoard;
  private AbstractPlayer manualPlayer;
  private ManualPlayerSalvoData manualData;
  private AbstractPlayer aiPlayer;

  private boolean beingTested;

  public BattleShipController(Readable input, Appendable appendable, boolean beingTested) {
    this.view = new View(input, appendable);
    this.beingTested = beingTested;
  }

  /***To run a game of BattleShip*/
  @Override
  public void run() {
    manualData = new ManualPlayerSalvoData();
    String name = getName();
    final int[] boardDimensions = getBoardDimensions();
    manualBoard = new GameState();
    aiBoard = new GameState();
    if (beingTested) {
      manualPlayer = new ManualPlayer(name, manualBoard, true, manualData);
      aiPlayer = new AiPlayer(name, aiBoard, true);
    } else {
      manualPlayer = new ManualPlayer(name, manualBoard, false, manualData);
      aiPlayer = new AiPlayer(name, aiBoard, false);
    }
    int fleetSize = Math.min(boardDimensions[0], boardDimensions[1]);
    Map<ShipType, Integer> specifications = getFleet(fleetSize);
    manualPlayer.setup(boardDimensions[0], boardDimensions[1], specifications);
    aiPlayer.setup(boardDimensions[0], boardDimensions[1], specifications);
    runSalvos(boardDimensions[0], boardDimensions[1]);
    endGame();
  }

  /***To output the result of the game for the manual player and send the game end information to
   *each player
   */
  private void endGame() {
    if (manualBoard.allSunk() && aiBoard.allSunk()) {
      view.output(GameResult.DRAW.reason);
      manualPlayer.endGame(GameResult.DRAW, GameResult.DRAW.reason);
      aiPlayer.endGame(GameResult.DRAW, GameResult.DRAW.reason);
    } else if (manualBoard.allSunk()) {
      view.output(GameResult.LOSS.reason);
      manualPlayer.endGame(GameResult.LOSS, GameResult.LOSS.reason);
      aiPlayer.endGame(GameResult.WIN, GameResult.WIN.reason);
    } else {
      view.output(GameResult.WIN.reason);
      aiPlayer.endGame(GameResult.LOSS, GameResult.LOSS.reason);
      manualPlayer.endGame(GameResult.WIN, GameResult.WIN.reason);
    }
  }

  /***To ensure the proper number of arguments are supplied per line in accordance with the game
   * design
   *
   * @param str the error message if an invalid amount of arguments are supplied
   *
   * @param split the number of arguments that should be on a line
   *
   * @return each argument as its own element in an array of strings
   */
  private String[] validateSameLine(String str, int split) {
    String s = view.input();
    String[] inputs = s.split(" ");
    while (inputs.length != split) {
      view.output(str);
      s = view.input();
      inputs = s.split(" ");
    }
    return inputs;
  }

  /***To run the battleship rounds, only stopping if one or both players' ships have sunk
   *
   * @param height the height of the board
   *
   * @param width the width of the board
   */
  private void runSalvos(int height, int width) {
    int coordX;
    int coordY;
    while (!manualBoard.allSunk() && !aiBoard.allSunk()) {
      manualData.removeAll();
      view.output("Opponent Board Data: \n"
          + manualBoard.toStringOpponentData() + "\n"
          + "Your Board: \n"
          + manualBoard.toString() + "\n"
          + "Please Enter Your "
          + Math.min(manualBoard.getUnsunkShipCount(), manualBoard.getOpenOpponentSquares())
          + " Shot(s) [The top left is (0,0), x increases to the right, y increases downwards]: \n"
          + "------------------------------------------------------------------------"
          + "---------------------------------\n");
      int salvoShots =
          Math.min(manualBoard.getUnsunkShipCount(), manualBoard.getOpenOpponentSquares());
      while (salvoShots > 0) {
        String[] inputs = validateSameLine("Please enter coordinate on SAME LINE: ", 2);
        try {
          coordX = Integer.parseInt(inputs[0]);
          coordY = Integer.parseInt(inputs[1]);
        } catch (NumberFormatException e) {
          view.output("Please ensure your inputs are spaced properly and are integers: ");
          continue;
        }
        if (Validation.salvoCoordinateValidation(coordX, coordY, manualBoard, height, width,
            manualData)) {
          manualData.enterSalvo(coordX, coordY);
          salvoShots--;
        } else {
          view.output("Invalid coordinate, please enter a shot that hasn't already "
              + "been attempted, isn't being attempted, and is within the board dimensions");
        }
      }
      List<Coord> manualShots = manualPlayer.takeShots();
      List<Coord> aiShots = aiPlayer.takeShots();
      aiPlayer.successfulHits(manualPlayer.reportDamage(aiShots));
      manualPlayer.successfulHits(aiPlayer.reportDamage(manualShots));
    }
  }

  /***Gets the name of the player
   *
   * @return a string representation of the player name
   */
  private String getName() {
    view.output("Please enter your first name: ");
    String[] inputs = validateSameLine("Please enter ONLY first name: ", 1);
    return inputs[0];
  }

  /***Gets the dimensions of the battleship board for both players
   *
   * @return an int array where the first element is the height and the second is the width
   */
  private int[] getBoardDimensions() {
    boolean showFailMessage = false;
    int width = -1;
    int height = -1;
    view.output("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below on the SAME LINE (ex. 6 6):\s
        ----------------------------------------------------------------------------------""");

    while (!Validation.boardValidation(height, width)) {
      if (showFailMessage) {
        view.output("""
            You've entered invalid dimensions. Please remember that the height and width\s
            of the game must be in the range (6, 15), inclusive. Try again!
            ----------------------------------------------------------------------------------""");
      }
      String[] inputs =
          validateSameLine("Please ensure your inputs are properly spaced and on the SAME LINE: ",
              2);
      try {
        height = Integer.parseInt(inputs[0]);
        width = Integer.parseInt(inputs[1]);
      } catch (NumberFormatException e) {
        view.output("Please ensure your inputs are spaced properly and are integers: ");
      }
      showFailMessage = true;
    }
    return new int[] {height, width};
  }

  /***Gets the map of ship types and their associated frequency for the battleship board
   *
   * @param fleetSize the total number of ships
   *
   * @return a map of ship-types and their relative frequencies
   */
  private Map<ShipType, Integer> getFleet(int fleetSize) {
    int carrierCount = -1;
    int battleShipCount = -1;
    int destroyerCount = -1;
    int submarineCount = -1;
    boolean showFailMessage = false;
    view.output(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
            + "Remember, your fleet may not exceed size " + fleetSize
            + " and there must be at least 1 of each ship");

    while (!Validation.fleetValidation(carrierCount, battleShipCount, destroyerCount,
        submarineCount, fleetSize)) {
      if (showFailMessage) {
        view.output("Uh Oh! You've entered invalid fleet sizes.\n"
            +
            "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
            + "Remember, your fleet may not exceed size " + fleetSize
            + " and there must be at least 1 of each ship");
      }
      String[] inputs = validateSameLine("Please enter ship counts on SAME LINE: ", 4);
      try {
        carrierCount = Integer.parseInt(inputs[0]);
        battleShipCount = Integer.parseInt(inputs[1]);
        destroyerCount = Integer.parseInt(inputs[2]);
        submarineCount = Integer.parseInt(inputs[3]);
      } catch (NumberFormatException e) {
        view.output("Please ensure your inputs are spaced properly and are integers: ");
      }
      showFailMessage = true;
    }
    HashMap<ShipType, Integer> fleetMap = new HashMap<>();
    fleetMap.put(ShipType.CARRIER, carrierCount);
    fleetMap.put(ShipType.BATTLESHIP, battleShipCount);
    fleetMap.put(ShipType.DESTROYER, destroyerCount);
    fleetMap.put(ShipType.SUBMARINE, submarineCount);
    return fleetMap;
  }

}
