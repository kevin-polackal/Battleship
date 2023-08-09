package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.ShipType;
import cs3500.pa04.model.FleetSpecJson;
import cs3500.pa04.model.SetupJson;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * tests for the SetupJson record
 */
public class SetupJsonTest {

  /**
   * Tests that getFleet properly returns a map of ships and their frequencies
   */
  @Test
  public void setupJson() {
    FleetSpecJson fleetSpecJson = new FleetSpecJson(1, 1, 1, 1);
    final SetupJson setupJson = new SetupJson(6, 6, fleetSpecJson);
    HashMap<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    assertEquals(setupJson.getFleet(), specifications);
  }
}