package autotests.clients.duckController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;

public class UpdateClient extends DuckClient {
    String duckUpdateApiPath = "/api/duck/update";

    public void updateDuck(TestCaseRunner runner, String color, double height, String id, String material,
                           String sound, String wingsState) {
        requstApiSixQueryParams(runner, duckUpdateApiPath, color, height, id, material, sound, wingsState);
    }

    public void validateResponseUpdate(TestCaseRunner runner, String id) {
        String body = "{\n" + "\"message\": \"Duck with id = " + id + " is updated\"\n" + "}";
        validateResponseStringJsonBody(runner, body);
    }
}