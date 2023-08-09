package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * To represent fleet information of a BattleSalvo game in json properties
 *
 * @param fleet the fleet of ships represented in ShipJson format
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipJson> fleet) {
}
