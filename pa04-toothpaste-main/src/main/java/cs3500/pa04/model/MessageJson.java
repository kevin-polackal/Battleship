package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * To represent information of messages being received and sent to the server
 *
 * @param methodName the method name attached to this json information
 * @param arguments  the json information in the form of a JsonNode
 */
public record MessageJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") JsonNode arguments) {
}
