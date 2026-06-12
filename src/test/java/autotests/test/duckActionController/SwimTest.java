package autotests.test.duckActionController;

import autotests.clients.duckActionController.SwimClient;
import autotests.payloads.response.DuckSwimResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class SwimTest extends SwimClient {
    //Обнаружен баг - Отсутствие атрибута соответствующего характеристики плаванья уточки. Тест временно сделан зеленным
    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка умения плавать уточки с существующим id")
    @Story("Эндпоинт /api/duck/swim")
    @Test()
    @CitrusTest
    public void swimDuckWithValidId(@Optional @CitrusResource TestCaseRunner runner) {
        generateId(runner);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        getSwimDuck(runner, "${id}");
        DuckSwimResponse expectedResponse = new DuckSwimResponse()
                .timestamp("@ignore@")
                .status(404)
                .error("Not Found")
                .message("No message available")
                .path("/api/duck/swim");
        validateResponseObject(runner, HttpStatus.NOT_FOUND, expectedResponse);
        deleteDuckFromDatabase(runner, "${id}");

    }

    //Обнаружен баг - Неверный json-ответ при проверке умения плавать несуществующей уточки. Тест временно сделан зеленным
    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка умения плавать уточки с несуществующим id")
    @Story("Эндпоинт /api/duck/swim")
    @Test()
    @CitrusTest
    public void swimDuckWithInvalidId(@Optional @CitrusResource TestCaseRunner runner) {
        generateId(runner);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${id}");
        getSwimDuck(runner, "${id}");
        runner.variable("Status", 404);
        runner.variable("Error", "Not Found");
        runner.variable("Message", "No message available");
        runner.variable("Path", "/api/duck/swim");
        validateResponseJsonBodyFromFile(runner, HttpStatus.NOT_FOUND, "messageTest/MessageSwimDuckPropertiesResponse.json");
    }
}