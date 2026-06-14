package autotests.test.duckController;

import autotests.clients.duckController.UpdateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

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
}