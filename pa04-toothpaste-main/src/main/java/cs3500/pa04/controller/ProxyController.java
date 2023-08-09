package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.controller.Controller;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.view.View;
import cs3500.pa04.model.CoordJson;
import cs3500.pa04.model.EndGameJson;
import cs3500.pa04.model.FleetJson;
import cs3500.pa04.model.JoinJson;
import cs3500.pa04.model.JsonUtils;
import cs3500.pa04.model.MessageJson;
import cs3500.pa04.model.MethodName;
import cs3500.pa04.model.SetupJson;
import cs3500.pa04.model.ShipJson;
import cs3500.pa04.model.VolleyJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to facilitate server communication and play a game of BattleSalvo
 */
public class ProxyController implements Controller {

  private final View playerFacing;
  private final Socket server;
  private final Player player;

  private final InputStream in;

  private final PrintStream out;
  private final ObjectMapper mapper = new ObjectMapper();

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("");


  /**
   * Constructor for the ProxyController
   *
   * @param server the server host and port being connected to
   * @throws IOException if the server streams cannot properly be connected
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.playerFacing = new View(new InputStreamReader(System.in), new PrintStream(System.out));
    this.server = server;
    this.player = player;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
  }

  /**
   * While the server is still open, run accepts a message from the server and feeds it to delegate
   * message to properly handle the message
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      playerFacing.output("Server has closed, goodbye!");
    }
  }

  /**
   * Given a MessageJson, using its method-name property it delegates parsing the information to one
   * of the six designated helper methods
   *
   * @param message the message from the server in the form of a MessageJson
   * @throws IOException if an invalid method name is supplied by the server
   */
  private void delegateMessage(MessageJson message) throws IOException {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    if (MethodName.JOIN.methodName.equals(name)) {
      handleJoin();
    } else if (MethodName.SETUP.methodName.equals(name)) {
      handleSetup(arguments);
    } else if (MethodName.TAKE_SHOTS.methodName.equals(name)) {
      handleTakeShots();
    } else if (MethodName.REPORT_DAMAGE.methodName.equals(name)) {
      handleReportDamage(arguments);
    } else if (MethodName.SUCCESSFUL_HITS.methodName.equals(name)) {
      handleSuccessfulHits(arguments);
    } else if (MethodName.END_GAME.methodName.equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }

  }

  /**
   * Handles end game messages from the server, gives an empty messageJson to the server
   *
   * @param arguments the specifics of how the game was ended
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson end = this.mapper.convertValue(arguments, EndGameJson.class);
    if (end.gameResult().equals("WIN")) {
      player.endGame(GameResult.WIN, end.reason());
      playerFacing.output("You won the game, congrats!");
    } else if (end.gameResult().equals("LOSE")) {
      player.endGame(GameResult.LOSS, end.reason());
      playerFacing.output("You lost the game, tough luck!");
    } else {
      player.endGame(GameResult.DRAW, end.reason());
      playerFacing.output("You drew, how peculiar!");
    }
    MessageJson outMessage = new MessageJson(MethodName.END_GAME.methodName, VOID_RESPONSE);
    this.out.println(JsonUtils.serializeRecord(outMessage));
  }

  /**
   * Handles successful-hits messages from the server, gives an empty messageJson to the server
   *
   * @param arguments the volley of coordinates from this player's take shots that hit opponent
   *                  ships
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    VolleyJson volleyInfo = this.mapper.convertValue(arguments, VolleyJson.class);
    List<CoordJson> shipsOpponentHitJson = volleyInfo.volley();
    List<Coord> shipsOpponentHit = new ArrayList<>();
    for (CoordJson cj : shipsOpponentHitJson) {
      shipsOpponentHit.add(new Coord(cj.x(), cj.y()));
    }
    player.successfulHits(shipsOpponentHit);
    MessageJson json = new MessageJson(MethodName.SUCCESSFUL_HITS.methodName, VOID_RESPONSE);
    this.out.println(JsonUtils.serializeRecord(json));
  }

  /**
   * Handles report-damage messages from the server, gives a volley of coordinates to the server
   * consisting of coordinates the opponent shot at which were ships
   *
   * @param arguments the volley of coordinates representing the opponent's take-shots
   */
  private void handleReportDamage(JsonNode arguments) {
    VolleyJson volleyInfo = this.mapper.convertValue(arguments, VolleyJson.class);
    List<CoordJson> opponentShotsJson = volleyInfo.volley();
    List<Coord> opponentShots = new ArrayList<>();
    for (CoordJson cj : opponentShotsJson) {
      opponentShots.add(new Coord(cj.x(), cj.y()));
    }
    List<Coord> hitShips = player.reportDamage(opponentShots);
    List<CoordJson> hitShipsJson = new ArrayList<>();
    for (Coord c : hitShips) {
      CoordJson coord = new CoordJson(c.getX(), c.getY());
      hitShipsJson.add(coord);
    }
    VolleyJson successfulVolley = new VolleyJson(hitShipsJson);
    MessageJson outMessage =
        new MessageJson(MethodName.REPORT_DAMAGE.methodName,
            JsonUtils.serializeRecord(successfulVolley));
    this.out.println(JsonUtils.serializeRecord(outMessage));
  }

  /**
   * Handles take-shots messages from the server, gives a volley of coordinates to the server
   * detailing the shot locations of this player's salvo
   */
  private void handleTakeShots() {
    List<Coord> shots = player.takeShots();
    List<CoordJson> shotsJson = new ArrayList<>();
    for (Coord c : shots) {
      CoordJson cj = new CoordJson(c.getX(), c.getY());
      shotsJson.add(cj);
    }
    VolleyJson volley = new VolleyJson(shotsJson);
    MessageJson outMessage =
        new MessageJson(MethodName.TAKE_SHOTS.methodName, JsonUtils.serializeRecord(volley));
    this.out.println(JsonUtils.serializeRecord(outMessage));
  }

  /**
   * Handles setup messages from the server, gives a fleet of this user's ships which detail each
   * ship's location to the server
   *
   * @param arguments The height of the board, the width of the board, and the quantities of each
   *                  type of ship in BattleSalvo
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupInfo = this.mapper.convertValue(arguments, SetupJson.class);
    List<Ship> shipList = player.setup(setupInfo.height(), setupInfo.width(), setupInfo.getFleet());
    List<ShipJson> shipJsonList = new ArrayList<>();
    for (Ship s : shipList) {
      Coord c = s.getFirstCoord();
      CoordJson coord = new CoordJson(c.getX(), c.getY());
      ShipJson ship = new ShipJson(coord, s.getShipLength(), s.getOrientation().representation);
      shipJsonList.add(ship);
    }
    FleetJson fleet = new FleetJson(shipJsonList);
    MessageJson outMessage =
        new MessageJson(MethodName.SETUP.methodName, JsonUtils.serializeRecord(fleet));
    this.out.println(JsonUtils.serializeRecord(outMessage));
  }

  /**
   * Handles join messages from the server, gives the name of this player along with the game type
   * to the server
   */
  private void handleJoin() {
    JoinJson joinInfo = new JoinJson("kevin-polackal", "SINGLE");
    MessageJson outMessage =
        new MessageJson(MethodName.JOIN.methodName, JsonUtils.serializeRecord(joinInfo));
    this.out.println(JsonUtils.serializeRecord(outMessage));
  }
}
