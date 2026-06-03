package autotests.duckTest.putRequest;

import autotests.baseDuckTest.BaseDuckTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class UpdateDuckTest extends BaseDuckTest {
    @Test(description = "Изменение цвета и высоты уточки")
    @CitrusTest
    public void updateColorAndHeightDuck(@Optional @CitrusResource TestCaseRunner runner) {
        final float HEIGHT = 1.5f;
        createDuckBase(runner, "yellow", 1, "wood", "quack", "ACTIVE");
        updateDuck(runner, "red", HEIGHT, "${duckId}", "rubber", "quack", "ACTIVE");
        validateResponseUpdate(runner, "${duckId}");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Изменение цвета и звука уточки")
    @CitrusTest
    public void updateColorAndSoundDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckBase(runner, "yellow", 1, "wood", "quack", "ACTIVE");
        updateDuck(runner, "red", 1, "${duckId}", "rubber", "quack-quack", "ACTIVE");
        validateResponseUpdate(runner, "${duckId}");
        deleteDuck(runner, "${duckId}");
    }

    public void updateDuck(TestCaseRunner runner, String color, double height, String id, String material,
                           String sound, String wingsState) {
        runner.$(http()
                .client("http://localhost:2222")
                .send()
                .put("/api/duck/update")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("id", id)
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
                .body("{\n" + "\"message\": \"Duck with id = " + id + " is updated\"\n" + "}"));
    }
}