package autotests.clients.duckController;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

public class DeleteClient extends DuckClient {
    String duckDeleteApiPath = "/api/duck/delete";

    public void deleteDuck(TestCaseRunner runner, String id) {
        String path = duckDeleteApiPath + "?id=" + id;
        requestApiWithParamsDelete(runner, path, duckService);

    }

    protected void validateDeleteDuckInDatabase(TestCaseRunner runner, String id) {
        runner.$(query(testDb)
                .statement("select COUNT(*) as count from DUCK where ID=" + id)
                .validate("count", "0"));
    }
}