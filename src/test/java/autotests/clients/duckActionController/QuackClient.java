package autotests.clients.duckActionController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class QuackClient extends DuckClient {
    String duckQuackApiPath = "/api/duck/action/quack";

    public void getQuackDuck(TestCaseRunner runner, String id, String repetitionCount, String soundCount) {
        String path = duckQuackApiPath + "?id=" + id + "&repetitionCount=" + repetitionCount + "&soundCount=" + soundCount;
        requestApiWithParamsGet(runner, path, duckService);
    }

    public void validateResponseQuack(TestCaseRunner runner, HttpStatus status, String value) {
        String body = "{\n" + "\"sound\": \"" + value + "\"\n" + "}";
        validateResponseStringJsonBody(runner, status, body);
    }

}