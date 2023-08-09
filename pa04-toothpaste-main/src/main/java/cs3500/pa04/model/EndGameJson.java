package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * To represent information being sent to the server associated with end-game
 *
 * @param gameResult The result of the game
 * @param reason     The reason of why the game ended the way it did
 */
public record EndGameJson(
    @JsonProperty("result") String gameResult,
    @JsonProperty("reason") String reason) {

}
