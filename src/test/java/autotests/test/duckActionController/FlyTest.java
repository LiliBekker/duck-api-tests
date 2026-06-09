package autotests.test.duckActionController;

import autotests.clients.duckActionController.FlyClient;
import autotests.payloads.response.DuckMessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class FlyTest extends FlyClient {


    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка умения летать уточки с существующим id и с активными крыльями")
    @Story("Эндпоинт /api/duck/action/fly")
    @Test()
    @CitrusTest
    public void flyDuckWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "1");
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        getFlyDuck(runner, "${duckId}");
        DuckMessageResponse expectedResponse = new DuckMessageResponse()
                .message("I am flying :)");
        validateResponseFly(runner, expectedResponse);
        deleteDuckFromDatabase(runner, "${duckId}");
    }


    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка умения летать уточки с существующим id и со связанными крыльями")
    @Story("Эндпоинт /api/duck/action/fly")
    @Test()
    @CitrusTest
    public void flyDuckWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "2");
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "rubber", "quack", "FIXED");
        getFlyDuck(runner, "${duckId}");
        runner.variable("message", "I can not fly :C");
        validateResponseFlyJson(runner, "messageTest/MessageDuckPropertiesResponse.json");
        deleteDuckFromDatabase(runner, "${duckId}");
    }



    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка умения летать уточки с существующим id и с крыльями в неопределенном состоянии")
    @Story("Эндпоинт /api/duck/action/fly")
    @Test()
    @CitrusTest
    public void flyDuckWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", "3");
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "rubber", "quack", "UNDEFINED");
        getFlyDuck(runner, "${duckId}");
        validateResponseFly(runner, "Wings are not detected :(");
        deleteDuckFromDatabase(runner, "${duckId}");
    }
}