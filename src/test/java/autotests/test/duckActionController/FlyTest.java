package autotests.test.duckActionController;

import autotests.clients.duckActionController.FlyClient;
import autotests.payloads.request.DuckPropertiesRequestCreate;
import autotests.payloads.response.DuckMessageResponse;
import autotests.payloads.response.DuckQuackResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class FlyTest extends FlyClient {

    @Test(description = "Проверка умения летать уточки с существующим id и с активными крыльями")
    @CitrusTest
    public void flyDuckWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequestCreate properties = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        createDuck(runner, properties);

        getFlyDuck(runner, "${duckId}");

        DuckMessageResponse expectedResponse = new DuckMessageResponse()
                .message("I am flying :)");

        validateResponseFly(runner, expectedResponse);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Проверка умения летать уточки с существующим id и со связанными крыльями")
    @CitrusTest
    public void flyDuckWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequestCreate properties = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");
        createDuck(runner, properties);
        getFlyDuck(runner, "${duckId}");
        runner.variable("message", "I can not fly :C");
        validateResponseFlyJson(runner, "messageTest/MessageDuckPropertiesResponse.json");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Проверка умения летать уточки с существующим id и с крыльями в неопределенном состоянии")
    @CitrusTest
    public void flyDuckWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        DuckPropertiesRequestCreate properties = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("UNDEFINED");
        createDuck(runner, properties);

        getFlyDuck(runner, "${duckId}");
        validateResponseFly(runner, "Wings are not detected :(");
        deleteDuck(runner, "${duckId}");
    }
}