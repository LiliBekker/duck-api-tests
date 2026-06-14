package autotests.test.duckController;

import autotests.clients.duckController.UpdateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Изменения уточки")
@Story("Эндпоинт /api/duck/update")
public class UpdateTest extends UpdateClient {
    @Test(description = "Изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorAndHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {
        generateDuckId(runner);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        updateDuck(runner, "red", 0.5, "${duckId}", "wood", "quack", "ACTIVE");
        runner.variable("message", "Duck with id = ${duckId} is updated");
        validateResponseUpdateJson(runner, "messageTest/MessageDuckPropertiesResponse.json");
        validateDuckInDatabase(runner, "${duckId}", "red", "0.5", "wood", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");
    }

    @Test(description = "Изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorAndSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {
        generateDuckId(runner);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        updateDuck(runner, "red", 0.03, "${duckId}", "wood", "quack-quack", "ACTIVE");
        runner.variable("message", "Duck with id = ${duckId} is updated");
        validateResponseUpdateJson(runner, "messageTest/MessageDuckPropertiesResponse.json");
        validateDuckInDatabase(runner, "${duckId}", "red", "0.03", "wood", "quack-quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");
    }
}