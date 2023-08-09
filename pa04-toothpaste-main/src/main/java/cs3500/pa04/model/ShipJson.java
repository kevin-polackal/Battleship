package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * To represent the information of a json representing a Ship in battleSalvo
 *
 * @param coord     the start coordinate of a ship in CoordJson format
 * @param length    the length of the ship
 * @param direction whether the ship lays horizontally or vertically
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {
}
