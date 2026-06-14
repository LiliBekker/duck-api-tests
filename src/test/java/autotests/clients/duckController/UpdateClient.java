package autotests.clients.duckController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import org.springframework.http.HttpStatus;

public class UpdateClient extends DuckClient {
    String duckUpdateApiPath = "/api/duck/update";

    public void updateDuck(TestCaseRunner runner, String color, double height, String id, String material,
                           String sound, String wingsState) {
        String path = duckUpdateApiPath
                + "?color=" + color
                + "&height=" + height
                + "&id=" + id
                + "&material=" + material
                + "&sound=" + sound
                + "&wingsState=" + wingsState;
        requestApiWithParamsPut(runner, path, duckService);
    }

    public void validateResponseUpdate(TestCaseRunner runner, HttpStatus status, String id) {
        String body = "{\n" + "\"message\": \"Duck with id = " + id + " is updated\"\n" + "}";
        validateResponseStringJsonBody(runner, status, body);
    }
}