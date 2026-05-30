package autotests.duckTest.deleteRequest;

import autotests.baseDuckTest.baseDuckTest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class deleteDuckTest extends baseDuckTest {
    @Test(description = "Удаление уточки")
    @CitrusTest
    public void createDuckWithMaterialWood(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner,
                "yellow",
                1,
                "wood",
                "quack",
                "ACTIVE");
        validateResponseCreate(runner,
                "yellow",
                1,
                "wood",
                "quack",
                "ACTIVE");
        deleteDuck(runner,
                "${duckId}");
        validateResponseDelete(runner);
    }
}
