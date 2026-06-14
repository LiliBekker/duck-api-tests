package autotests.test.duckController;

import autotests.clients.duckController.DeleteClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Удаление уточки")
@Story("Эндпоинт /api/duck/delete")
public class DeleteTest extends DeleteClient {

    @Test(description = "Удаление уточки")
    @CitrusTest
    public void deleteDuckInDatabase(@Optional @CitrusResource TestCaseRunner runner) {
        generateDuckId(runner);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        runner.variable("message", "Duck is deleted");
        deleteDuck(runner, "${duckId}");
        validateResponseDelete(runner, "messageTest/MessageDuckPropertiesResponse.json");
        validateDeleteDuckInDatabase(runner, "${duckId}");
    }
}