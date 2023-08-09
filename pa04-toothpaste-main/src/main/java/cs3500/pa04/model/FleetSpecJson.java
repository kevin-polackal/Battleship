package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.HashMap;
import java.util.Map;

/**
 * To represent information regarding a fleet of ships through json properties
 *
 * @param carrier    the number of carriers
 * @param battleship the number of battleships
 * @param destroyer  the number of destroyer
 * @param submarine  the number of submarines
 */
public record FleetSpecJson(
    @JsonProperty("CARRIER") int carrier,
    @JsonProperty("BATTLESHIP") int battleship,
    @JsonProperty("DESTROYER") int destroyer,
    @JsonProperty("SUBMARINE") int submarine) {

  /**
   * Returns the fleet in a Map format
   *
   * @return the fleet of ships
   */
  public Map<ShipType, Integer> getFleet() {
    Map<ShipType, Integer> fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, this.carrier);
    fleet.put(ShipType.BATTLESHIP, this.battleship);
    fleet.put(ShipType.DESTROYER, this.destroyer);
    fleet.put(ShipType.SUBMARINE, this.submarine);
    return fleet;
  }
}
