package autotests.test.duckController;

import autotests.clients.duckController.CreateClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateTest extends CreateClient {

    @Test(description = "Проверка создания уточки с material = rubber")
    @CitrusTest
    public void createDuckWithMaterialRubber(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1, "rubber", "quack", "ACTIVE");
        validateResponseCreate(runner, "yellow", 1, "rubber", "quack", "ACTIVE");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Проверка создания уточки с material = wood")
    @CitrusTest
    public void createDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1, "wood", "quack", "ACTIVE");
        validateResponseCreate(runner, "yellow", 1, "wood", "quack", "ACTIVE");
        deleteDuck(runner, "${duckId}");
    }
}