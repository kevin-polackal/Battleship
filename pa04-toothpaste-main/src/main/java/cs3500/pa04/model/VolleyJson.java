package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * To represent the information of a json with method-name Volley
 *
 * @param volley a list of coordinates in CoordJson format
 */
public record VolleyJson(
    @JsonProperty("coordinates") List<CoordJson> volley) {
}
