package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the cell class
 */
class CellTest {

  private Cell cell;

  @BeforeEach
  public void setup() {
    cell = new Cell(false, false, new Coord(0, 0));
    cell.setShipType(ShipType.SUBMARINE);
  }

  /**
   * Tests that setAsShip properly sets this cell as a ship cell
   */
  @Test
  public void testSetAsShip() {
    assertFalse(cell.isShip());
    cell.setAsShip();
    assertTrue(cell.isShip());
  }

  /**
   * Tests that setAsHit properly sets this cell to a hit cell
   */
  @Test
  public void testSetAsHit() {
    assertFalse(cell.isHit());
    cell.setAsHit();
    assertTrue(cell.isHit());
  }

  /**
   * Tests that isShip properly determines if this cell is a ship
   */
  @Test
  public void testIsShip() {
    assertFalse(cell.isShip());
  }

  /**
   * Tests that isHit properly determines if this cell is hit
   */
  @Test
  public void testIsHit() {
    assertFalse(cell.isHit());
  }

  /**
   * Tests that getCoord properly returns the coordinate of this cell
   */
  @Test
  public void testGetCoord() {
    assertEquals(cell.getCoord(), new Coord(0, 0));
  }

  /**
   * Tests that setCoord properly sets this cell's coordinate to the given coordinate
   */
  @Test
  public void testSetCoord() {
    assertEquals(cell.getCoord(), new Coord(0, 0));
    cell.setCoord(new Coord(5, 5));
    assertNotEquals(cell.getCoord(), new Coord(0, 0));
    assertEquals(cell.getCoord(), new Coord(5, 5));
  }

  /**
   * Tests that getShipType returns this cell's ship-type
   */
  @Test
  public void testGetShipType() {
    assertEquals(cell.getShipType(), ShipType.SUBMARINE);
  }

  /**
   * Tests that setShipType properly sets this cell's ship-type to the given ship-type
   */
  @Test
  public void testSetShipType() {
    assertEquals(cell.getShipType(), ShipType.SUBMARINE);
    cell.setShipType(ShipType.CARRIER);
    assertEquals(cell.getShipType(), ShipType.CARRIER);
  }

}