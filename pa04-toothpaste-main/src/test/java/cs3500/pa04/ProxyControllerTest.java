package cs3500.pa04;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.GameState;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.model.CoordJson;
import cs3500.pa04.model.EndGameJson;
import cs3500.pa04.model.FleetSpecJson;
import cs3500.pa04.model.JsonUtils;
import cs3500.pa04.model.MessageJson;
import cs3500.pa04.model.SetupJson;
import cs3500.pa04.model.VolleyJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ProxyControllerTest {

  Player player;
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("");
  private ByteArrayOutputStream testLog;
  private ProxyController dealer;

  @BeforeEach
  public void setup() {
    player = new AiPlayer("kevin-polackal", new GameState(), true);
    HashMap<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    player.setup(6, 6, specifications);
    player.takeShots();
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Tests that a end-game request results in a properly formatted end-game being sent to the server
   */
  @Test
  public void testHandleEndGameWin() {
    // Create sample EndGame
    EndGameJson endGameJson = new EndGameJson("WIN", "You beat your opponent");
    JsonNode jsonNode = createSampleMessage("end-game", endGameJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    this.dealer.run();
    String expected = "{\"method-name\":\"end-game\",\"arguments\":\"\"}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Tests that a end-game request results in a properly formatted end-game being sent to the server
   */
  @Test
  public void testHandleEndGameLose() {
    // Create sample EndGame
    EndGameJson endGameJson = new EndGameJson("LOSE", "You beat your opponent");
    JsonNode jsonNode = createSampleMessage("end-game", endGameJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    this.dealer.run();
    String expected = "{\"method-name\":\"end-game\",\"arguments\":\"\"}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Tests that a end-game request results in a properly formatted end-game being sent to the server
   */
  @Test
  public void testHandleEndGameDraw() {
    // Create sample EndGame
    EndGameJson endGameJson = new EndGameJson("DRAW", "You beat your opponent");
    JsonNode jsonNode = createSampleMessage("end-game", endGameJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    this.dealer.run();
    String expected = "{\"method-name\":\"end-game\",\"arguments\":\"\"}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Tests that a successful-hits request results in a properly formatted successful-hits
   * being sent to the server
   */
  @Test
  public void testSuccessfulHits() {
    List<CoordJson> coordJson =
        Arrays.asList(new CoordJson(0, 0), new CoordJson(1, 2), new CoordJson(5, 5));

    // Create sample EndGame
    VolleyJson volleyJson = new VolleyJson(coordJson);
    JsonNode jsonNode = createSampleMessage("successful-hits", volleyJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    this.dealer.run();
    String expected = "{\"method-name\":\"successful-hits\",\"arguments\":\"\"}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Tests that a report-damage request results in a properly formatted report-damage
   * being sent to the server
   */
  @Test
  public void testReportDamage() {
    List<CoordJson> coordJson =
        Arrays.asList(new CoordJson(0, 4), new CoordJson(1, 4), new CoordJson(2, 4));

    // Create sample EndGame
    VolleyJson volleyJson = new VolleyJson(coordJson);
    JsonNode jsonNode = createSampleMessage("report-damage", volleyJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    this.dealer.run();
    String expected =
        "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":"
            + "[{\"x\":0,\"y\":4},{\"x\":1,\"y\":4},{\"x\":2,\"y\":4}]}}\n";
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  /**
   * Tests that a take-shots request results in a properly formatted take-shots being
   * sent to the server
   */
  @Test
  public void testTakeShots() {
    MessageJson messageJson = new MessageJson("take-shots", VOID_RESPONSE);
    JsonNode jsonNode = JsonUtils.serializeRecord(messageJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    this.dealer.run();
    responseToClass(MessageJson.class);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Tests that a setup request results in a properly formatted setup being sent to the server
   */
  @Test
  public void testHandleSetup() {
    FleetSpecJson fleetSpecJson = new FleetSpecJson(1, 1, 1, 1);
    SetupJson setupJson = new SetupJson(6, 6, fleetSpecJson);


    // Create sample EndGame
    JsonNode jsonNode = createSampleMessage("setup", setupJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    this.dealer.run();
    String expected =
        "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\":0,\"y\":4},"
            + "\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":1,\"y\":0},"
            + "\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":2,\"y\":2},"
            + "\"length\":4,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":3,\"y\":3},"
            + "\"length\":3,\"direction\":\"HORIZONTAL\"}]}}\n";
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  /**
   * Tests that a join request results in a properly formatted join being sent to the server
   */
  @Test
  public void testHandleJoin() {
    MessageJson messageJson = new MessageJson("join", VOID_RESPONSE);
    JsonNode jsonNode = JsonUtils.serializeRecord(messageJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    this.dealer.run();
    String expected =
        "{\"method-name\":\"join\",\"arguments\":"
            + "{\"name\":\"kevin-polackal\",\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  /**
   * Tests an invalid method name properly throws an error if it isn't 1 of the 6 names
   */
  @Test
  public void testError() {
    MessageJson messageJson = new MessageJson("bad-name", VOID_RESPONSE);
    JsonNode jsonNode = JsonUtils.serializeRecord(messageJson);

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a dealer
    try {
      this.dealer = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    assertThrows(IllegalStateException.class, () -> this.dealer.run());
  }


  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}