package autotests.clients;

import autotests.EndpointConfig;
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
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {
    String duckCreateApiPath = "/api/duck/create";
    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

    public void generateDuckId(TestCaseRunner runner) {
        runner.variable("duckId", new Random().nextInt(1_000_000));
    }

    public void updateDataBase(TestCaseRunner runner, String query) {
        runner.$(sql(testDb)
                .statement(query));
    }

    public void createDuckInDatabase(TestCaseRunner runner, String id, String color, String height, String material,
                                     String sound, String wingsState) {

        String query = "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (" + id + ", '" + color + "', " + height + ", '" + material + "', '" + sound + "', '" + wingsState + "');";
        updateDataBase(runner, query);
    }


    public void deleteDuckFromDatabase(TestCaseRunner runner, String id) {
        String query = "delete from DUCK\n" +
                "where id = " + id + ";";
        updateDataBase(runner, query);
    }

    protected void validateDuckInDatabase(TestCaseRunner runner, String id, String color, String height,
                                          String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR", color)
                .validate("HEIGHT", height)
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }


    //методы для отправки запроса post c помощью Object body
    public void requestApiObject(TestCaseRunner runner, String apiPath, Object body) {
        runner.$(http()
                .client(duckService)
                .send()
                .post(duckCreateApiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }
    //методы для отправки запроса get
    public void requestApiGet(TestCaseRunner runner, String apiPath, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .get(apiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id));
    }
    //методы для отправки запроса delete
    public void requestApiDelete(TestCaseRunner runner, String apiPath, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete(apiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id));
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