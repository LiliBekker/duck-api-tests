package autotests.clients;

import autotests.BaseTest;
import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.Random;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends BaseTest {




    public void createDuckInDatabase(TestCaseRunner runner, String id, String color, String height, String material,
                                     String sound, String wingsState) {

        String query = "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                "values (" + id + ", '" + color + "', " + height + ", '" + material + "', '" + sound + "', '" + wingsState + "');";
        updateDataBase(runner, query);
    }


    public void deleteDuckFromDatabase(TestCaseRunner runner, String id) {
        String query = "delete from DUCK\n" +
                "where id = " + id + ";";
        updateDataBase(runner, query);
    }

    // методы валидации данных БД
    protected void validateDuckInDatabase(TestCaseRunner runner, String id, String color, String height,
                                          String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR", color)
                .validate("HEIGHT", height)
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }

}