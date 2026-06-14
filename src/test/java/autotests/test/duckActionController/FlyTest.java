package autotests.test.duckActionController;

import autotests.clients.duckActionController.FlyClient;
import autotests.payloads.response.DuckMessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class FlyTest extends FlyClient {


    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка умения летать уточки с существующим id и с активными крыльями")
    @Story("Эндпоинт /api/duck/action/fly")
    @Test()
    @CitrusTest
    public void flyDuckWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        generateId(runner);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        getFlyDuck(runner, "${id}");
        DuckMessageResponse expectedResponse = new DuckMessageResponse()
                .message("I am flying :)");
        validateResponseObject(runner, HttpStatus.OK, expectedResponse);
        deleteDuckFromDatabase(runner, "${id}");
    }


    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка умения летать уточки с существующим id и со связанными крыльями")
    @Story("Эндпоинт /api/duck/action/fly")
    @Test()
    @CitrusTest
    public void flyDuckWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        generateId(runner);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "rubber", "quack", "FIXED");
        getFlyDuck(runner, "${id}");
        runner.variable("message", "I can not fly :C");
        validateResponseJsonBodyFromFile(runner, HttpStatus.OK, "messageTest/MessageDuckPropertiesResponse.json");
        deleteDuckFromDatabase(runner, "${id}");
    }

    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка умения летать уточки с существующим id и с крыльями в неопределенном состоянии")
    @Story("Эндпоинт /api/duck/action/fly")
    @Test()
    @CitrusTest
    public void flyDuckWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        generateId(runner);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "rubber", "quack", "UNDEFINED");
        getFlyDuck(runner, "${id}");
        validateResponseFly(runner, HttpStatus.OK, "Wings are not detected :(");
        deleteDuckFromDatabase(runner, "${id}");
    }
}