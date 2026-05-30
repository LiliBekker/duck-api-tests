package autotests.duckTest.getRequest;

import autotests.baseDuckTest.baseDuckTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class flyDuckTest extends baseDuckTest {

    @Test(description = "Проверка умения летать уточки с существующим id и с активными крыльями")
    @CitrusTest
    public void flyDuckWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1, "rubber", "quack", "ACTIVE");
        validateResponseCreate(runner, "yellow", 1, "rubber", "quack", "ACTIVE");
        getFlyDuck(runner, "${duckId}");
        validateResponseFly(runner, "I am flying :)");
        deleteDuck(runner, "${duckId}");
        validateResponseDelete(runner);
    }

    @Test(description = "Проверка умения летать уточки с существующим id и со связанными крыльями")
    @CitrusTest
    public void flyDuckWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1, "rubber", "quack", "FIXED");
        validateResponseCreate(runner, "yellow", 1, "rubber", "quack", "FIXED");
        getFlyDuck(runner, "${duckId}");
        validateResponseFly(runner, "I can not fly :C");
        deleteDuck(runner, "${duckId}");
        validateResponseDelete(runner);
    }

    @Test(description = "Проверка умения летать уточки с существующим id и с крыльями в неопределенном состоянии")
    @CitrusTest
    public void flyDuckWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 1, "rubber", "quack", "UNDEFINED");
        validateResponseCreate(runner, "yellow", 1, "rubber", "quack", "UNDEFINED");
        getFlyDuck(runner, "${duckId}");
        validateResponseFly(runner, "Wings are not detected :(");
        deleteDuck(runner, "${duckId}");
        validateResponseDelete(runner);
    }

    public void getFlyDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/fly")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id));
    }

    public void validateResponseFly(TestCaseRunner runner, String message) {
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "\"message\": \"" + message + "\"\n" +
                        "}"));
    }
}