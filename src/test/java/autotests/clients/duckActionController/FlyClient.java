package autotests.clients.duckActionController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import org.springframework.http.HttpStatus;

public class FlyClient extends DuckClient {
    String duckFlyApiPath = "/api/duck/action/fly";

    public void getFlyDuck(TestCaseRunner runner, String id) {
        String path = duckFlyApiPath + "?id=" + id;
        requestApiWithParamsGet(runner, path, duckService);
    }

    public void validateResponseFly(TestCaseRunner runner, HttpStatus status,  String message) {
        String body = "{\n" + "\"message\": \"" + message + "\"\n" + "}";
        validateResponseStringJsonBody(runner, status, body);
    }
}