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
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateTest extends CreateClient {
    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка создания уточки с material = rubber")
    @Story("Эндпоинт /api/duck/create")
    @Test()
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
        validateResponseWithIdObject(runner, HttpStatus.OK, expectedResponse);
        deleteDuckFromDatabase(runner, "${duckId}");
    }

    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка создания уточки с material = wood")
    @Story("Эндпоинт /api/duck/create")
    @Test()
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
        validateResponseWithIdJsonBodyFromFile(runner, HttpStatus.OK, "createTest/CreateDuckPropertiesResponse.json");
        deleteDuckFromDatabase(runner, "${duckId}");
    }
}