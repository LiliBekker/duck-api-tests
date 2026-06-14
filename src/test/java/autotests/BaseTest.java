package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import java.util.Random;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

    public void generateId(TestCaseRunner runner) {
        runner.variable("id", new Random().nextInt(1_000_000));
    }

    public void updateDataBase(TestCaseRunner runner, String query) {
        runner.$(sql(testDb)
                .statement(query));
    }

    // метод для отправки запроса с параметрами
    public void requestApiWithParamsGet(TestCaseRunner runner, String apiPath, HttpClient httpClient) {
        runner.$(http()
                .client(httpClient)
                .send()
                .get(apiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    public void requestApiWithParamsPut(TestCaseRunner runner, String apiPath, HttpClient httpClient) {
        runner.$(http()
                .client(httpClient)
                .send()
                .put(apiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    //методы для отправки запроса delete
    public void requestApiWithParamsDelete(TestCaseRunner runner, String apiPath, HttpClient httpClient) {
        runner.$(http()
                .client(httpClient)
                .send()
                .delete(apiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }


    //методы для отправки запроса post c помощью Object body
    public void requestApiObject(TestCaseRunner runner, String apiPath, Object body, HttpClient httpClient) {
        runner.$(http()
                .client(httpClient)
                .send()
                .post(apiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    // методы для валидации ответа
    public void validateResponseObject(TestCaseRunner runner, HttpStatus status, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    public void validateResponseStringJsonBody(TestCaseRunner runner, HttpStatus status, String expectedBody) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(expectedBody));
    }

    public void validateResponseWithIdObject(TestCaseRunner runner, HttpStatus status, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId"))
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    public void validateResponseJsonBodyFromFile(TestCaseRunner runner, HttpStatus status, String expectedPayloadPath) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayloadPath)));
    }


    public void validateResponseWithIdJsonBodyFromFile(TestCaseRunner runner, HttpStatus status, String expectedPayloadPath) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId"))
                .body(new ClassPathResource(expectedPayloadPath)));
    }
}
