package autotests.test.duckController;

import autotests.clients.duckController.CreateClient;
import autotests.payloads.request.DuckPropertiesRequestCreate;
import autotests.payloads.response.DuckPropertiesResponseCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Создание уточки")
@Story("Эндпоинт /api/duck/create")
public class CreateTest extends CreateClient {

    @Test(description = "Проверка создания уточки с material = rubber")
    @CitrusTest
    public void createDuckWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequestCreate properties = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        DuckPropertiesResponseCreate expectedResponse = new DuckPropertiesResponseCreate()
                .color("yellow")
                .height(0.03)
                .id("@isNumber()@")
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, properties);
        validateResponseCreate(runner, expectedResponse);
        validateDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");
    }

    @Test(description = "Проверка создания уточки с material = wood")
    @CitrusTest
    public void createDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequestCreate properties = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.03)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, properties);
        runner.variable("duckMaterial", "wood");
        validateResponseCreate(runner, "createTest/CreateDuckPropertiesResponse.json");
        validateDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");
    }
}