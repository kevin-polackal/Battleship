package cs3500.pa03.model;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Opponent Cell Class
 */
class OpponentCellTest {
  private OpponentCell oppCell;

  @BeforeEach
  public void setup() {
    oppCell = new OpponentCell(new Coord(11, 2));
  }

  /**
   * Tests that setHit properly set the Opponent Cell as hit
   */
  @Test
  public void testSetHit() {
    assertFalse(oppCell.getHit());
    oppCell.setHit();
    assertTrue(oppCell.getHit());
  }

  /**
   * Tests that setMiss properly set the Opponent Cell as miss
   */
  @Test
  public void testSetMiss() {
    assertFalse(oppCell.getMiss());
    oppCell.setMiss();
    assertTrue(oppCell.getMiss());
  }

  /**
   * Tests that getHit properly gets whether this Opponent cell has been hit
   */
  @Test
  public void testGetHit() {
    assertFalse(oppCell.getHit());
  }

  /**
   * Tests that getMiss properly gets whether this Opponent cell has been missed
   */
  @Test
  public void testGetMiss() {
    assertFalse(oppCell.getMiss());
  }

  /**
   * Tests that getCoord properly gets the coordinate of this Opponent cell
   */
  @Test
  public void testGetCoord() {
    assertEquals(oppCell.getCoord(), new Coord(11, 2));
  }

}