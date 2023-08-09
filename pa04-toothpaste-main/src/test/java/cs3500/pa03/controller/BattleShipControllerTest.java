package cs3500.pa03.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the BattleShipController class
 */
class BattleShipControllerTest {
  private BattleShipController controller;
  private StringWriter writer;

  @BeforeEach
  public void setup() {
    writer = new StringWriter();
  }

  /**
   * Test for the controller, given a file of multiple bad consisting of an example battleship
   * session including multiple lines of erroneous data
   * See at: controllerInputs.sr in main/resources
   */
  @Test
  public void testControllerPlayerWins() {
    try {
      controller =
          new BattleShipController(new FileReader("src/main/resources/controllerInputs.sr"), writer,
              true);
    } catch (IOException e) {
      fail();
    }
    controller.run();
    assertTrue(writer.toString().endsWith("You beat your opponent!\n"));
  }

  /**
   * Test for when the player loses in battleship
   * See at: controllerInputsBad.sr
   */
  @Test
  public void testControllerPlayerLoss() {
    try {
      controller =
          new BattleShipController(new FileReader("src/main/resources/controllerInputsBad.sr"),
              writer, true);
    } catch (IOException e) {
      fail();
    }
    controller.run();
    assertEquals("You lost to your opponent!\n",
        writer.toString().substring(writer.toString().length() - 27));
  }

}