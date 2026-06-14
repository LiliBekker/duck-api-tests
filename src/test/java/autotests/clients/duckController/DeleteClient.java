package autotests.clients.duckController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DeleteClient extends DuckClient {

    @Step("Проверка удаления уточки через БД")
    protected void validateDeleteDuckInDatabase(TestCaseRunner runner, String id) {
        runner.$(query(testDb)
                .statement("select COUNT(*) as count from DUCK where ID=" + id)
                .validate("count", "0"));
    }

    @Step("Проверка получения ответа от сервера по сообщению")
    public void validateResponseDelete(TestCaseRunner runner) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" + "\"message\": \"Duck is deleted\"\n" + "}"));
    }

    @Step("Проверка получения ответа от сервера по ожидаемому объекту")
    public void validateResponseDelete(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    @Step("Проверка получения ответа от сервера по json-файлу")
    public void validateResponseDelete(TestCaseRunner runner, String expectedPayloadPath) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayloadPath)));
    }
}