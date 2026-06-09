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

public class UpdateTest extends UpdateClient {


    @Epic("Тесты на duck-action-controller")
    @Feature("Изменение цвета и высоты уточки")
    @Story("Эндпоинт /api/duck/update")
    @Test()
    @CitrusTest
    public void updateColorAndHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {
        generateDuckId(runner);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        updateDuckInDatabase(runner, "${duckId}", "red", "0.5", "wood", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}", "red", "0.5", "wood", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");
    }


    @Epic("Тесты на duck-action-controller")
    @Feature("Изменение цвета и звука уточки")
    @Story("Эндпоинт /api/duck/update")
    @Test()
    @CitrusTest
    public void updateColorAndSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {
        generateDuckId(runner);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        updateDuckInDatabase(runner, "${duckId}", "red", "0.5", "wood", "quack-quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}", "red", "0.5", "wood", "quack-quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");
    }
}