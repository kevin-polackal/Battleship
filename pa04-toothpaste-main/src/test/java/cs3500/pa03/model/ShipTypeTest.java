package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the ShipType enum
 */
class ShipTypeTest {

  @Test
  public void testShipType() {
    assertNotEquals(ShipType.BATTLESHIP, ShipType.CARRIER);
    assertNotEquals(ShipType.SUBMARINE, ShipType.DESTROYER);
  }

}