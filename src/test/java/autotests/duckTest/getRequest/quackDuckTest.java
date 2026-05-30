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

public class quackDuckTest extends baseDuckTest {

    private static final String ODD_ID = "2";
    private static final String EVEN_ID = "3";

    @Test(description = "Проверка кряканья уточки с корректный нечётный id и корректным звуком")
    @CitrusTest
    public void quackDuckWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        getQuackDuck(runner, ODD_ID, "1", "1");
        validateResponseQuack(runner, "moo");
    }

    @Test(description = "Проверка кряканья уточки с корректный чётный id и корректным звуком")
    @CitrusTest
    public void quackDuckWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        getQuackDuck(runner, EVEN_ID, "1", "1");
        validateResponseQuack(runner, "quack");
    }

    public void getQuackDuck(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .get("/api/duck/action/quack")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id)
                .queryParam("repetitionCount", String.valueOf(repetitionCount))
                .queryParam("soundCount", String.valueOf(soundCount)));
    }

    public void validateResponseQuack(TestCaseRunner runner, String sound) {
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "\"sound\": \"" + sound + "\"\n" +
                        "}"));
    }
}