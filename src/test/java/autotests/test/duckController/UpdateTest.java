package autotests.test.duckController;

import autotests.clients.duckController.UpdateClient;
import autotests.payloads.request.DuckPropertiesRequestCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

public class UpdateTest extends UpdateClient {
    @Epic("Тесты на duck-action-controller")
    @Feature("Изменение цвета и высоты уточки")
    @Story("Эндпоинт /api/duck/update")
    @Test()
    @CitrusTest
    public void updateColorAndHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {
        generateId(runner);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        updateDuck(runner, "red", 0.5, "${id}", "wood", "quack", "ACTIVE");
        runner.variable("message", "Duck with id = ${id} is updated");
        validateResponseJsonBodyFromFile(runner, HttpStatus.OK, "messageTest/MessageDuckPropertiesResponse.json");
        validateDuckInDatabase(runner, "${id}", "red", "0.5", "wood", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${id}");
    }


    @Epic("Тесты на duck-action-controller")
    @Feature("Изменение цвета и звука уточки")
    @Story("Эндпоинт /api/duck/update")
    @Test()
    @CitrusTest
    public void updateColorAndSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {
        generateId(runner);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        updateDuck(runner, "red", 0.03, "${id}", "wood", "quack-quack", "ACTIVE");
        runner.variable("message", "Duck with id = ${id} is updated");
        validateResponseJsonBodyFromFile(runner, HttpStatus.OK, "messageTest/MessageDuckPropertiesResponse.json");
        validateDuckInDatabase(runner, "${id}", "red", "0.03", "wood", "quack-quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${id}");
    }


    @Epic("Тесты на duck-action-controller")
    @Feature("Изменение свойств уточки (параметризованный тест)")
    @Story("Эндпоинт /api/duck/update")
    @Test(dataProvider = "ducks")
    @CitrusTest
    @CitrusParameters({"properties", "expected", "runner"})
    public void updateDuckTest(
            DuckPropertiesRequestCreate properties,
            DuckPropertiesRequestCreate expected,
            @Optional @CitrusResource TestCaseRunner runner) {
        generateId(runner);
        createDuckInDatabase(runner, "${id}", properties.color(), String.valueOf(properties.height()),
                properties.material(), properties.sound(), properties.wingsState());
        updateDuck(runner, expected.color(), expected.height(), "${id}", expected.material(), expected.sound(),
                expected.wingsState());
        validateResponseJsonBodyFromFile(runner, HttpStatus.OK,
                "messageTest/MessageDuckPropertiesResponse.json");
        validateDuckInDatabase(runner, "${id}", expected.color(), String.valueOf(expected.height()),
                expected.material(), expected.sound(), expected.wingsState());
        deleteDuckFromDatabase(runner, "${id}");
    }


    @DataProvider(name = "ducks")
    public static Object[][] ducks() {
        DuckPropertiesRequestCreate yellowWoodActiveDuck = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.3)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");

        DuckPropertiesRequestCreate redRubberFixedDuck = new DuckPropertiesRequestCreate()
                .color("red")
                .height(0.5)
                .material("rubber")
                .sound("quack-quack")
                .wingsState("FIXED");

        DuckPropertiesRequestCreate blueMetalActiveDuck = new DuckPropertiesRequestCreate()
                .color("blue")
                .height(1.0)
                .material("metal")
                .sound("quack")
                .wingsState("ACTIVE");

        DuckPropertiesRequestCreate greenPlasticFixedDuck = new DuckPropertiesRequestCreate()
                .color("green")
                .height(0.7)
                .material("plastic")
                .sound("quack-quack")
                .wingsState("FIXED");

        DuckPropertiesRequestCreate orangeWoodUndefinedDuck = new DuckPropertiesRequestCreate()
                .color("orange")
                .height(0.2)
                .material("wood")
                .sound("quack")
                .wingsState("UNDEFINED");

        return new Object[][]{
                {yellowWoodActiveDuck, redRubberFixedDuck, null},
                {blueMetalActiveDuck, greenPlasticFixedDuck, null},
                {redRubberFixedDuck, blueMetalActiveDuck, null},
                {greenPlasticFixedDuck, orangeWoodUndefinedDuck, null},
                {orangeWoodUndefinedDuck, yellowWoodActiveDuck, null}
        };
    }
}