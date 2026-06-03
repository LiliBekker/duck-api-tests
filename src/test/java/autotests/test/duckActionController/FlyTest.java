package autotests.test.duckActionController;

import autotests.clients.duckActionController.FlyClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class FlyTest extends FlyClient {

    @Test(description = "Проверка умения летать уточки с существующим id и с активными крыльями")
    @CitrusTest
    public void flyDuckWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckBase(runner, "yellow", 1, "rubber", "quack", "ACTIVE");
        getFlyDuck(runner, "${duckId}");
        validateResponseFly(runner, "I am flying :)");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Проверка умения летать уточки с существующим id и со связанными крыльями")
    @CitrusTest
    public void flyDuckWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckBase(runner, "yellow", 1, "rubber", "quack", "FIXED");
        getFlyDuck(runner, "${duckId}");
        validateResponseFly(runner, "I can not fly :C");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Проверка умения летать уточки с существующим id и с крыльями в неопределенном состоянии")
    @CitrusTest
    public void flyDuckWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckBase(runner, "yellow", 1, "rubber", "quack", "UNDEFINED");
        getFlyDuck(runner, "${duckId}");
        validateResponseFly(runner, "Wings are not detected :(");
        deleteDuck(runner, "${duckId}");
    }
}