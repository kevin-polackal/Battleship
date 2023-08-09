package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.ShipType;
import cs3500.pa04.model.FleetSpecJson;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * Tests for the FleetSpecJson record
 */
public class FleetSpecJsonTest {

  /**
   * Tests that getFleet properly returns the correct map of ships
   */
  @Test
  public void testFleetSpec() {
    final FleetSpecJson fleetSpecJson = new FleetSpecJson(1, 1, 1, 1);
    HashMap<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    assertEquals(fleetSpecJson.getFleet(), specifications);
  }
}
