package autotests.clients.duckController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;

public class CreateClient extends DuckClient {
    String duckCreateApiPath = "/api/duck/create";

    public void createDuck(TestCaseRunner runner, Object duckData) {
        requstApiObject(runner, duckCreateApiPath, duckData);
    }

    public void validateResponseCreate(TestCaseRunner runner, String color, double height,
                                       String material, String sound, String wingsState) {
        String body = "{\n" +
                "\"id\": \"@variable('duckId')@\",\n" +
                "\"color\": \"" + color + "\",\n" +
                "\"height\": " + height + ",\n" +
                "\"material\": \"" + material + "\",\n" +
                "\"sound\": \"" + sound + "\",\n" +
                "\"wingsState\": \"" + wingsState + "\"\n" + "}";
        validateResponseStringJsonBody(runner, body);
    }
}