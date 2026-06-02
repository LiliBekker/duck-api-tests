package autotests.duckTest.getRequest;

import autotests.baseDuckTest.BaseDuckTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class PropertiesDuckTest extends BaseDuckTest {

    private static final String ODD_ID = "1";
    private static final String EVEN_ID = "2";

    @Test(description = "Проверка вывода данных о уточке с четным id и значением поля material = wood)")
    @CitrusTest
    public void propertiesDuckWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        getPropertiesDuck(runner, EVEN_ID);
        validateResponseProperties(runner, "yellow", 1.0, "wood", "quack", "ACTIVE");
    }

    @Test(description = "Проверка вывода данных о уточке с четным id и значением поля material = rubber)")
    @CitrusTest
    public void propertiesDuckWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        getPropertiesDuck(runner, ODD_ID);
        validateResponseProperties(runner, "yellow", 1.0, "rubber", "quack", "ACTIVE");
    }

    public void getPropertiesDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/properties")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id));
    }

    public void validateResponseProperties(TestCaseRunner runner, String color, double height,
                                           String material, String sound, String wingsState) {
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "\"color\": \"" + color + "\",\n" +
                        "\"height\": " + height + ",\n" +
                        "\"material\": \"" + material + "\",\n" +
                        "\"sound\": \"" + sound + "\",\n" +
                        "\"wingsState\": \"" + wingsState + "\"\n" +
                        "}"));
    }
}