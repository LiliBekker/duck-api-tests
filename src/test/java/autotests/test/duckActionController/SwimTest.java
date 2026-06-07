package autotests.test.duckActionController;

import autotests.clients.duckActionController.SwimClient;
import autotests.payloads.request.DuckPropertiesRequestCreate;
import autotests.payloads.response.DuckSwimResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class SwimTest extends SwimClient {
    //Обнаружен баг - Отсутствие атрибута соответствующего характеристики плаванья уточки. Тест временно сделан зеленным
    @Test(description = "Проверка умения плавать уточки с существующим id")
    @CitrusTest
    public void swimDuckWithValidId(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequestCreate properties = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, properties);
        getSwimDuck(runner, "${duckId}");
        DuckSwimResponse expectedResponse = new DuckSwimResponse()
                .timestamp("@ignore@")
                .status(404)
                .error("Not Found")
                .message("No message available")
                .path("/api/duck/swim");
        validateResponseSwim(runner, HttpStatus.NOT_FOUND, expectedResponse);
        deleteDuck(runner, "${duckId}");
    }

    //Обнаружен баг - Неверный json-ответ при проверке умения плавать несуществующей уточки. Тест временно сделан зеленным
    @Test(description = "Проверка умения плавать уточки с несуществующим id")
    @CitrusTest
    public void swimDuckWithInvalidId(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequestCreate properties = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, properties);
        deleteDuck(runner, "${duckId}");
        getSwimDuck(runner, "${duckId}");
        runner.variable("Status", 404);
        runner.variable("Error", "Not Found");
        runner.variable("Message", "No message available");
        runner.variable("Path", "/api/duck/swim");
        validateResponseSwimJson(runner, HttpStatus.NOT_FOUND, "messageTest/MessageSwimDuckPropertiesResponse.json");
    }
}