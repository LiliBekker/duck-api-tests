package autotests.test.duckActionController;

import autotests.clients.duckActionController.QuackClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class QuackTest extends QuackClient {

    private static final String ODD_ID = "3";
    private static final String EVEN_ID = "2";

    @Test(description = "Проверка кряканья уточки с корректным нечётным id и корректным звуком")
    @CitrusTest
    public void quackDuckWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        getQuackDuck(runner, ODD_ID, "1", "1");
        validateResponseQuack(runner, "sound", "quack");
    }

    @Test(description = "Проверка кряканья уточки с корректный чётный id и корректным звуком")
    @CitrusTest
    public void quackDuckWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        getQuackDuck(runner, EVEN_ID, "1", "1");
        validateResponseQuack(runner, "sound", "moo");
    }
}