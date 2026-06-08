package autotests.test.duckController;

import autotests.clients.duckController.DeleteClient;
import autotests.payloads.request.DuckPropertiesRequestCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DeleteTest extends DeleteClient {
    @Test(description = "Удаление уточки")
    @CitrusTest
    public void deleteDuck(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequestCreate properties = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.03)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, properties);
        runner.variable("message", "Duck is deleted");
        deleteDuck(runner, "${duckId}");
        validateResponseDelete(runner, "messageTest/MessageDuckPropertiesResponse.json");
    }
}