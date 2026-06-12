package autotests.clients.duckActionController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class SwimClient extends DuckClient {
    String duckSwimApiPath = "/api/duck/swim";

    public void getSwimDuck(TestCaseRunner runner, String id) {
        requestApiGet(runner, duckSwimApiPath, id);
    }

    public void validateResponseSwim(TestCaseRunner runner, HttpStatus status, String statusNum, String err, String mas) {
        String body = "{\n" +
                "  \"timestamp\": \"@ignore@\",\n" +
                "  \"status\":" + statusNum + ",\n" +
                "  \"error\": \"" + err + "\",\n" +
                "  \"message\": \"" + mas + "\",\n" +
                "  \"path\": \"/api/duck/swim\"\n" +
                "}";
        validateResponseStringJsonBody(runner, status, body);
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