package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for a ManualPlayer's takeShots
 */
class ManualPlayerTest {
  private AbstractPlayer player;

  private GameState board;

  private ManualPlayerSalvoData salvo;

  private HashMap<ShipType, Integer> specifications;

  @BeforeEach
  public void setup() {
    salvo = new ManualPlayerSalvoData();
    salvo.enterSalvo(0, 0);
    salvo.enterSalvo(1, 1);
    board = new GameState();
    player = new ManualPlayer("Joe", board, true, salvo);
    specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    GameState board2 = new GameState();
    AbstractPlayer player2 = new ManualPlayer("Joe", board2, false, salvo);
    player2.setup(6, 15, specifications);
  }

  /**
   * Tests that a manual player's takeShots is simply the salvo created through a user's input
   */
  @Test
  public void testTakeShots() {
    player.setup(6, 6, specifications);
    assertEquals(salvo.getSalvo(), player.takeShots());
    salvo.removeAll();
    salvo.enterSalvo(5, 5);
    salvo.enterSalvo(4, 3);
    List<Coord> coords = Arrays.asList(new Coord(5, 5), new Coord(4, 3));
    assertEquals(coords, player.takeShots());
  }
}