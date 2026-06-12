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
    String duckDeleteApiPath = "/api/duck/delete";
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








    // рефракторинг кода
    public void requstApiObject(TestCaseRunner runner, String apiPath, Object body) {
        runner.$(http()
                .client(duckService)
                .send()
                .post(duckCreateApiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    public void requstApiSixQueryParams(TestCaseRunner runner, String apiPath, String color, double height, String id, String material,
                                        String sound, String wingsState) {
        runner.$(http()
                .client(duckService)
                .send()
                .put(apiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("id", id)
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState));
    }

    public void requstApiQueryParam(TestCaseRunner runner, String apiPath, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete(apiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id));
    }

    public void validateResponseStringJsonBody(TestCaseRunner runner, String expectedBody) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(expectedBody));
    }


    public void validateResponseObject(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    public void validateResponseWthiIdObject(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId"))
                .body(new ObjectMappingPayloadBuilder(expectedPayload, new ObjectMapper())));
    }

    public void validateResponseJsonBodyFromFile(TestCaseRunner runner, String expectedPayloadPath) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .body(new ClassPathResource(expectedPayloadPath)));
    }

    public void validateResponseWthiIdJsonBodyFromFile(TestCaseRunner runner, String expectedPayloadPath) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId"))
                .body(new ClassPathResource(expectedPayloadPath)));
    }
}