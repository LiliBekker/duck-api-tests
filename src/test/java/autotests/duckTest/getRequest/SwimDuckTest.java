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

public class SwimDuckTest extends BaseDuckTest {
    //Обнаружен баг - Отсутствие атрибута соответствующего характеристики плаванья уточки
    @Test(description = "Проверка умения плавать уточки с существующим id")
    @CitrusTest
    public void swimDuckWithValidId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckBase(runner, "yellow", 1, "wood", "quack", "ACTIVE");
        getSwimDuck(runner, "${duckId}");
        validateResponseSwim(runner, HttpStatus.OK, "I'm swimming");
        deleteDuck(runner, "${duckId}");
    }

    //Обнаружен баг - Неверный json-ответ при проверке умения плавать несуществующей уточки
    @Test(description = "Проверка умения плавать уточки с несуществующим id")
    @CitrusTest
    public void swimDuckWithInvalidId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckBase(runner, "yellow", 1, "rubber", "quack", "ACTIVE");
        deleteDuck(runner, "${duckId}");
        getSwimDuck(runner, "${duckId}");
        validateResponseSwim(runner, HttpStatus.NOT_FOUND, "Not Found");
    }

    public void getSwimDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/swim")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id));
    }

    public void validateResponseSwim(TestCaseRunner runner, HttpStatus status, String mas) {
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" + "\"message\": \"" + mas + "\"\n" + "}"));
    }
}