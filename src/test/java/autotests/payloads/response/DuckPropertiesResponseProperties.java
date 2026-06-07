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
public class DuckPropertiesResponseProperties {
    @JsonProperty("color")
    String color;

    @JsonProperty("height")
    double height;

    @JsonProperty("material")
    String material;

    @JsonProperty("sound")
    String sound;

    @JsonProperty("wingsState")
    String wingsState;
}
