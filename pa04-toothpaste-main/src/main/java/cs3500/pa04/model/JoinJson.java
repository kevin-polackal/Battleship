package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * To represent the information of a join request being sent to the server
 *
 * @param name     the name of the player
 * @param gameType the type of game they want to play
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType) {
}
