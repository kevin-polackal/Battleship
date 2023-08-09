package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * To represent a Coordinate with Json properties
 *
 * @param x the x coordinate
 * @param y the y coordinate
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {

}
