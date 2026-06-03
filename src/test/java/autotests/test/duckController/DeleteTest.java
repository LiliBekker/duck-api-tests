package autotests.test.duckController;

import autotests.clients.duckController.DeleteClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DeleteTest extends DeleteClient {
    @Test(description = "Удаление уточки")
    @CitrusTest
    public void deleteDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckBase(runner, "yellow", 1, "wood", "quack", "ACTIVE");
        deleteDuck(runner, "${duckId}");
        validateResponseDelete(runner);
    }
}