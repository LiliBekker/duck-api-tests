package autotests.test.duckActionController;

import autotests.clients.duckActionController.QuackClient;
import autotests.payloads.response.DuckQuackResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class QuackTest extends QuackClient {

    private static final String ODD_ID = "3";
    private static final String EVEN_ID = "2";


    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка кряканья уточки с корректным нечётным id и корректным звуком")
    @Story("Эндпоинт /api/duck/action/quack")
    @Test()
    @CitrusTest
    public void quackDuckWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("id", ODD_ID);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        getQuackDuck(runner, "${id}", "1", "1");
        DuckQuackResponse expectedResponse = new DuckQuackResponse()
                .sound("quack");
        validateResponseObject(runner, HttpStatus.OK,  expectedResponse);
        deleteDuckFromDatabase(runner, "${id}");
    }


    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка кряканья уточки с корректный чётный id и корректным звуком")
    @Story("Эндпоинт /api/duck/action/quack")
    @Test()
    @CitrusTest
    public void quackDuckWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("id", EVEN_ID);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        getQuackDuck(runner, "${id}", "1", "1");
        runner.variable("sound", "moo");
        validateResponseJsonBodyFromFile(runner, HttpStatus.OK, "messageTest/MessageQuackDuckPropertiesResponse.json");
        deleteDuckFromDatabase(runner, "${id}");
    }
}