package autotests.clients.duckController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class UpdateClient extends DuckClient {
    String duckUpdateApiPath ="/api/duck/update";

    public void updateDuck(TestCaseRunner runner, String color, double height, String id, String material,
                           String sound, String wingsState) {
        runner.$(http()
                .client(duckService)
                .send()
                .put(duckUpdateApiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("id", id)
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState));
    }

    public void validateResponseUpdate(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" + "\"message\": \"Duck with id = " + id + " is updated\"\n" + "}"));
    }

    public void validateResponseUpdate(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    public void validateResponseUpdateJson(TestCaseRunner runner, String expectedPayloadPath) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayloadPath)));
    }
}