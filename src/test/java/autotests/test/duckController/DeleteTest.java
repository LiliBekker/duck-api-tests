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
    public void deleteDuckInDatabase(@Optional @CitrusResource TestCaseRunner runner) {
        generateDuckId(runner);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        deleteDuckFromDatabase(runner, "${duckId}");
        validateDeleteDuckInDatabase(runner, "${duckId}");
    }
}