package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Shot enum
 */
class ShotTest {

  @Test
  public void testShotTest() {
    assertNotEquals(Shot.HIT, Shot.MISS);
    assertNotEquals(Shot.MISS, Shot.NONE);
  }

}