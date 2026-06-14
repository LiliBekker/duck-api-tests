package autotests.test.duckActionController;

import autotests.clients.duckActionController.QuackClient;
import autotests.payloads.response.DuckQuackResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-action-controller")
@Feature("Звук уточки")
@Story("Эндпоинт /api/duck/action/quack")
public class QuackTest extends QuackClient {

    private static final String ODD_ID = "3";
    private static final String EVEN_ID = "2";

    @Test(description = "Проверка кряканья уточки с корректным нечётным id и корректным звуком")
    @CitrusTest
    public void quackDuckWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", ODD_ID);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        getQuackDuck(runner, "${duckId}", "1", "1");
        DuckQuackResponse expectedResponse = new DuckQuackResponse()
                .sound("quack");
        validateResponseQuack(runner, expectedResponse);
        deleteDuckFromDatabase(runner, "${duckId}");
    }

    @Test(description = "Проверка кряканья уточки с корректный чётный id и корректным звуком")
    @CitrusTest
    public void quackDuckWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", EVEN_ID);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        getQuackDuck(runner, "${duckId}", "1", "1");
        runner.variable("sound", "moo");
        validateResponseQuackjson(runner, "messageTest/MessageQuackDuckPropertiesResponse.json");
        deleteDuckFromDatabase(runner, "${duckId}");
    }
}