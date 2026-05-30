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

public class swimDuckTest extends baseDuckTest {
    @Test(description = "Проверка умения плавать уточки с существующим id")
    @CitrusTest
    public void swimDuckWithValidId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner,
                "yellow",
                1,
                "wood",
                "quack",
                "ACTIVE");
        validateResponseCreate(runner,
                "yellow",
                1,
                "wood",
                "quack",
                "ACTIVE");

        getSwimDuck(runner, "${duckId}");
        validateResponseSwimOK(runner, "I’m swimming");

        deleteDuck(runner, "${duckId}");
        validateResponseDelete(runner);
    }


    @Test(description = "Проверка умения плавать уточки с несуществующим id")
    @CitrusTest
    public void swimDuckWithInvalidId(@Optional @CitrusResource TestCaseRunner runner) {
        getSwimDuck(runner, "1000000");
        validateResponseSwimNotFound(runner, "Paws are not found ((((");
    }

    public void getSwimDuck(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/swim")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id));
    }

    public void validateResponseSwimOK(TestCaseRunner runner, String mas) {
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "\"message\": \"" + mas + "\"\n" +
                        "}"));
    }

    public void validateResponseSwimNotFound(TestCaseRunner runner, String mas) {
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.NOT_FOUND)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "\"message\": \"" + mas + "\"\n" +
                        "}"));
    }
}
