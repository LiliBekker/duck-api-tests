package autotests.clients.duckActionController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import org.springframework.http.HttpStatus;

public class PropertiesClient extends DuckClient {
    String duckPropertiesApiPath = "/api/duck/action/properties";

    public void getPropertiesDuck(TestCaseRunner runner, String id) {
        requestApiGet(runner, duckPropertiesApiPath, id);
    }

    public void validateResponseProperties(TestCaseRunner runner, HttpStatus status, String color, double height,
                                           String material, String sound, String wingsState) {
        String body = "{\n" +
                "\"color\": \"" + color + "\",\n" +
                "\"height\": " + height + ",\n" +
                "\"material\": \"" + material + "\",\n" +
                "\"sound\": \"" + sound + "\",\n" +
                "\"wingsState\": \"" + wingsState + "\"\n" +
                "}";
        validateResponseStringJsonBody(runner, status, body);
    }
}