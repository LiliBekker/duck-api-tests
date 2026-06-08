package autotests.payloads.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DuckSwimResponse {
    @JsonProperty("timestamp")
    String timestamp;

    @JsonProperty("status")
    Object status;

    @JsonProperty("error")
    String error;

    @JsonProperty("message")
    String message;

    @JsonProperty("path")
    String path;
}
