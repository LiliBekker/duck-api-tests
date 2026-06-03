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
        final float HEIGHT = 1.5f;
        createDuckBase(runner, "yellow", 1, "wood", "quack", "ACTIVE");
        updateDuck(runner, "red", HEIGHT, "${duckId}", "rubber", "quack", "ACTIVE");
        validateResponseUpdate(runner, "${duckId}");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorAndSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckBase(runner, "yellow", 1, "wood", "quack", "ACTIVE");
        updateDuck(runner, "red", 1, "${duckId}", "rubber", "quack-quack", "ACTIVE");
        validateResponseUpdate(runner, "${duckId}");
        deleteDuck(runner, "${duckId}");
    }
}