package autotests.clients.duckActionController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class SwimClient extends DuckClient {
    String duckSwimApiPath = "/api/duck/swim";

    public void getSwimDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get(duckSwimApiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id));
    }

    public void validateResponseSwim(TestCaseRunner runner, HttpStatus status, String mas) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "  \"timestamp\": \"@ignore@\",\n" +
                        "  \"status\": 404,\n" +
                        "  \"error\": \"Not Found\",\n" +
                        "  \"message\": \"" + mas + "\",\n" +
                        "  \"path\": \"/api/duck/swim\"\n" +
                        "}"));
    }

    public void validateResponseSwim(TestCaseRunner runner, HttpStatus status, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    public void validateResponseSwimJson(TestCaseRunner runner, HttpStatus status, String expectedPayloadPath) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayloadPath)));
    }
}