package cs3500.pa03.model;



import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * To test an AI Player's takeShots
 */
class AiPlayerTest {
  private AbstractPlayer player;

  private GameState board;


  private HashMap<ShipType, Integer> specifications;

  @BeforeEach
  public void setup() {
    board = new GameState();
    player = new AiPlayer("Joe", board, true);
    specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    GameState board2 = new GameState();
    AbstractPlayer player2 = new AiPlayer("Joe", board2, false);
    player2.setup(6, 15, specifications);
  }

  /**
   * Tests  that this AI Player does not repeatedly take the same shots
   */
  @Test
  public void testTakeShots() {
    player.setup(6, 6, specifications);
    assertNotEquals(player.takeShots(), player.takeShots());
    assertNotEquals(player.takeShots(), player.takeShots());
  }
}