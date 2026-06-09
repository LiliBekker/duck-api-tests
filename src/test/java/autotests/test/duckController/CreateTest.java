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
        generateDuckId(runner);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");

    }

    @Test(description = "Проверка создания уточки с material = wood")
    @CitrusTest
    public void createDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        generateDuckId(runner);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        validateDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");
    }
}