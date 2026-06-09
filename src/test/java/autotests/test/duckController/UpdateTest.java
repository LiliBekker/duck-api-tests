package autotests.test.duckController;

import autotests.clients.duckController.UpdateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class UpdateTest extends UpdateClient {
    @Test(description = "Изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorAndHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {
        generateDuckId(runner);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        updateDuckInDatabase(runner, "${duckId}", "red", "0.5", "wood", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}", "red", "0.5", "wood", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");
    }

    @Test(description = "Изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorAndSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {
        generateDuckId(runner);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        updateDuckInDatabase(runner, "${duckId}", "red", "0.5", "wood", "quack-quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}", "red", "0.5", "wood", "quack-quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");
    }
}