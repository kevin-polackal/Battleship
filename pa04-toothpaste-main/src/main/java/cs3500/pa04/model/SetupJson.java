package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;

/**
 * To represent information of a json with method name setup
 *
 * @param width     the width of the board
 * @param height    the height of the board
 * @param fleetSpec the fleet counts of the ship in FleetSpecJson format
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") FleetSpecJson fleetSpec) {

  public Map<ShipType, Integer> getFleet() {
    return this.fleetSpec.getFleet();
  }
}
