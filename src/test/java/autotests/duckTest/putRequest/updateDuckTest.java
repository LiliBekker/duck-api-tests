package autotests.duckTest.putRequest;

import autotests.baseDuckTest.baseDuckTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Random;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class updateDuckTest extends baseDuckTest {
    @Test(description = "Изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorAndHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {
        double randomHeight = new Random().nextDouble() * 10;

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
        updateDuck(runner,
                "${duckId}",
                "red",
                randomHeight,
                "rubber",
                "quack",
                "ACTIVE");
        validateResponseUpdate(runner,
                "${duckId}");
        deleteDuck(runner,
                "${duckId}");
        validateResponseDelete(runner);
    }


    @Test(description = "Изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorAndSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {
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
        updateDuck(runner,
                "${duckId}",
                "red",
                1,
                "rubber",
                "quack-quack",
                "ACTIVE");
        validateResponseUpdate(runner,
                "${duckId}");
        deleteDuck(runner,
                "${duckId}");
        validateResponseDelete(runner);
    }

    public void updateDuck(TestCaseRunner runner, String id, String color, double height, String material,
                           String sound, String wingsState) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .put("/api/duck/update")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id", id)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState));
    }

    public void validateResponseUpdate(TestCaseRunner runner, String id) {
        runner.$(http()
                .client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "\"message\": \"Duck with id = " + id + " is updated\"\n" +
                        "}"));
    }
}
