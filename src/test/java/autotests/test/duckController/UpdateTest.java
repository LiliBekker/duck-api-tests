package autotests.test.duckController;

import autotests.clients.duckController.UpdateClient;
import autotests.payloads.request.DuckPropertiesRequestCreate;
import autotests.payloads.response.DuckMessageResponse;
import autotests.payloads.response.DuckPropertiesResponseCreate;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class UpdateTest extends UpdateClient {
    @Test(description = "Изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorAndHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequestCreate properties = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.03)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, properties);
        updateDuck(runner, "red", 0.5, "${duckId}", "rubber", "quack", "ACTIVE");
        runner.variable("message", "Duck with id = ${duckId} is updated");
        validateResponseUpdateJson(runner, "messageTest/MessageDuckPropertiesResponse.json");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorAndSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequestCreate properties = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.03)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, properties);
        updateDuck(runner, "red", 0.03, "${duckId}", "rubber", "quack-quack", "ACTIVE");
        runner.variable("message", "Duck with id = ${duckId} is updated");
        validateResponseUpdateJson(runner, "messageTest/MessageDuckPropertiesResponse.json");
        deleteDuck(runner, "${duckId}");
    }
}