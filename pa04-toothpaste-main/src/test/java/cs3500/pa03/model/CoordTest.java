package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Coord class
 */
class CoordTest {
  private Coord coord;

  @BeforeEach
  public void setup() {
    coord = new Coord(0, 5);
  }

  /**
   * Tests that getX and getY properly return the respective x and y values of this Coord
   */
  @Test
  public void testGetters() {
    assertEquals(coord.getX(), 0);
    assertEquals(coord.getY(), 5);
  }

  /**
   * Tests the notion of equality of two Coords: if their Xs and Ys are equal, respectively
   */
  @Test
  public void testEquals() {
    assertTrue(coord.equals(new Coord(0, 5)));
    assertFalse(coord.equals(1));
  }
}