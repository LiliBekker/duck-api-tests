package autotests.clients.duckActionController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class FlyClient extends DuckClient {
    String duckFlyApiPath = "/api/duck/action/fly";

    @Step("Отправка запроса на умение уточки летать через метод /api/duck/action/fly")
    public void getFlyDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get(duckFlyApiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id));
    }

    @Step("Проверка получения ответа от сервера по сообщению")
    public void validateResponseFly(TestCaseRunner runner, String message) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" + "\"message\": \"" + message + "\"\n" + "}"));
    }

    @Step("Проверка получения ответа от сервера по ожидаемому объекту")
    public void validateResponseFly(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    @Step("Проверка получения ответа от сервера по json-файлу")
    public void validateResponseFlyJson(TestCaseRunner runner, String expectedPayloadPath) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayloadPath)));
    }
}