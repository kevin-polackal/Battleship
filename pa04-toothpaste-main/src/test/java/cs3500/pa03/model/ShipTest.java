package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Ship class
 */
class ShipTest {

  private GameState board;

  private List<Coord> sub1Coords;

  private Ship sub1;

  @BeforeEach
  public void setup() {
    board = new GameState();
    board.initBoard(6, 6);
    sub1Coords = Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    sub1 = new Ship(sub1Coords);
    board.putShip(ShipType.SUBMARINE, sub1);
    board.updateCells(sub1Coords);
  }

  /**
   * Tests that getCoords gets the proper coordinates of this ship
   */
  @Test
  public void testGetCoords() {
    List<Coord> coords = Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertEquals(sub1.getCoords(), coords);

  }

  /**
   * Tests that contains correctly determines whether this ship contains the given coordinate
   */
  @Test
  public void testContains() {
    assertTrue(sub1.contains(new Coord(1, 0)));
    assertFalse(sub1.contains(new Coord(0, 1)));
  }


}