package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void updateDataBase(TestCaseRunner runner, String query) {
        runner.$(sql(testDb)
                .statement(query));
    }

    @Step("Генерация рандомного значения id для создания уточки" )
    public void generateDuckId(TestCaseRunner runner) {
        runner.variable("duckId", new Random().nextInt(1_000_000));
    }

    @Step("Отправка запроса на создание уточки через метод /api/duck/create и получения id уточки" )
    public void createDuckBase(TestCaseRunner runner, String color, double height, String material,
                               String sound, String wingsState) {
        runner.$(http()
                .client(duckService)
                .send()
                .post(duckCreateApiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "\"color\": \"" + color + "\",\n" +
                        "\"height\": " + height + ",\n" +
                        "\"material\": \"" + material + "\",\n" +
                        "\"sound\": \"" + sound + "\",\n" +
                        "\"wingsState\": \"" + wingsState + "\"\n" + "}"));

        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId")));
    }

    @Step("Отправка запроса на создание уточки через метод /api/duck/create и получения id уточки" )
    public void createDuck(TestCaseRunner runner, Object duckData) {
        runner.$(http()
                .client(duckService)
                .send()
                .post(duckCreateApiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .type(MessageType.JSON)
                .body(new ObjectMappingPayloadBuilder(duckData, new ObjectMapper())));

        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }

    @Step("Создание уточки с помощью запроса к БД" )
    public void createDuckInDatabase(TestCaseRunner runner, String id, String color, String height, String material,
                                     String sound, String wingsState) {

        String query = "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (" + id + ", '" + color + "', " + height + ", '" + material + "', '" + sound + "', '" + wingsState + "');";
        updateDataBase(runner, query);
    }

    @Step("Отправка запроса на удаление уточки через метод /api/duck/delete")
    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete(duckDeleteApiPath)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id));
    }

    @Step("Удаление уточки с помощью запроса к БД" )
    public void deleteDuckFromDatabase(TestCaseRunner runner, String id) {
        String query = "delete from DUCK\n" +
                "where id = " + id + ";";
        updateDataBase(runner, query);
    }

    @Step("Проверка создания уточки в БД" )
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
}