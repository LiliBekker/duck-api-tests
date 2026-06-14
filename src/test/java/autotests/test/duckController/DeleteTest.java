package autotests.test.duckController;

import autotests.clients.duckController.DeleteClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DeleteTest extends DeleteClient {

    @Epic("Тесты на duck-action-controller")
    @Feature("Удаление уточки")
    @Story("Эндпоинт /api/duck/delete")
    @Test()
    @CitrusTest
    public void deleteDuckInDatabase(@Optional @CitrusResource TestCaseRunner runner) {
        generateId(runner);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        runner.variable("message", "Duck is deleted");
        deleteDuck(runner, "${id}");
        validateResponseJsonBodyFromFile(runner, HttpStatus.OK, "messageTest/MessageDuckPropertiesResponse.json");
        validateDeleteDuckInDatabase(runner, "${id}");
    }
}