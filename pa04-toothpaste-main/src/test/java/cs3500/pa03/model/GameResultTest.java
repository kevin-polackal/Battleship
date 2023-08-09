package cs3500.pa03.model;



import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the GameResult enum
 */
class GameResultTest {
  @Test
  public void testGameResult() {
    assertNotEquals(GameResult.WIN, GameResult.LOSS);
    assertNotEquals(GameResult.LOSS, GameResult.DRAW);
  }

}