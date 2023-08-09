package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/***Abstract class to represent various players of a battleship game
 */
public abstract class AbstractPlayer implements Player {
  private final String name;
  protected final GameState board;

  private List<Ship> playerShips;

  protected List<Coord> opponentShots;

  protected boolean beingTested;

  protected List<Coord> hitsThisRound = new ArrayList<>();


  /**
   * Constructor for AbstractPlayer
   *
   * @param name        The name of this player
   * @param board       This player's board
   * @param beingTested Whether we are testing the program
   */
  public AbstractPlayer(String name, GameState board, boolean beingTested) {
    this.name = name;
    this.board = board;
    this.beingTested = beingTested;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.board.initBoard(height, width);
    List<Ship> shipList = new ArrayList<>();
    shipList.addAll(
        getShips(height, width, ShipType.CARRIER, specifications.get(ShipType.CARRIER)));
    shipList.addAll(
        getShips(height, width, ShipType.BATTLESHIP, specifications.get(ShipType.BATTLESHIP)));
    shipList.addAll(
        getShips(height, width, ShipType.DESTROYER, specifications.get(ShipType.DESTROYER)));
    shipList.addAll(
        getShips(height, width, ShipType.SUBMARINE, specifications.get(ShipType.SUBMARINE)));
    playerShips = shipList;
    board.setShips(shipList);
    return shipList;
  }

  /**
   * Generates a list of ships of the given ship-type and number of said ships
   *
   * @param height    the height of the board
   * @param width     the width of the board
   * @param shipType  the type of ship
   * @param frequency how many of this ship is desired
   * @return a list of ships with the desired ship type and frequency
   */
  private List<Ship> getShips(int height, int width, ShipType shipType, int frequency) {
    List<Ship> ships = new ArrayList<>();
    List<Coord> coords;
    int seed = 0;
    while (frequency > 0) {
      if (this.beingTested) {
        coords =
            getShipCoordinates(height, width, shipType.shipLength, new Random(seed),
                new Random(seed));
      } else {
        coords =
            getShipCoordinates(height, width, shipType.shipLength, new Random(), new Random());
      }
      if (Validation.shipCoordinateValidation(height, width, board, coords)) {
        Ship ship = new Ship(coords);
        board.putShip(shipType, ship);
        ships.add(ship);
        frequency--;
      }
      seed++;
    }
    return ships;
  }

  /**
   * Generates a random set of coordinates for a ship of given length
   *
   * @param height     the height of the board
   * @param width      the width of the board
   * @param shipLength the length of the ship for which the coordinates are being generated
   * @return the list of coordinates representing the ship's position
   */
  private List<Coord> getShipCoordinates(int height, int width, int shipLength, Random horizOrVert,
                                         Random randomCoordinate) {
    List<Coord> coords = new ArrayList<>();
    boolean horizontal = horizOrVert.nextBoolean();
    if (horizontal) {
      int x = randomCoordinate.nextInt(0, width);
      int y = randomCoordinate.nextInt(0, height);
      coords.add(new Coord(x, y));
      for (int i = 1; i < shipLength; i++) {
        coords.add(new Coord(x + i, y));
      }
    } else {
      int x = randomCoordinate.nextInt(0, width);
      int y = randomCoordinate.nextInt(0, height);
      coords.add(new Coord(x, y));
      for (int i = 1; i < shipLength; i++) {
        coords.add(new Coord(x, y + i));
      }
    }
    return coords;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> damages = new ArrayList<>();
    for (Coord c : opponentShotsOnBoard) {
      for (Ship s : playerShips) {
        if (s.contains(c)) {
          damages.add(c);
        }
      }
    }
    board.updateCells(opponentShotsOnBoard);

    return damages;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    hitsThisRound.addAll(shotsThatHitOpponentShips);
    board.updateOpponentData(opponentShots, shotsThatHitOpponentShips);
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }
}
