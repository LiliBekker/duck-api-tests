package autotests.test.duckActionController;

import autotests.clients.duckActionController.PropertiesClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class PropertiesTest extends PropertiesClient {

    private static final String ODD_ID = "1";
    private static final String EVEN_ID = "2";

    //Обнаружен баг - Пустое тело ответа при значении поля material = «wood» и четном id
    @Test(description = "Проверка вывода данных о уточке с четным id и значением поля material = wood)")
    @CitrusTest
    public void propertiesDuckWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        getPropertiesDuck(runner, EVEN_ID);
        validateResponseProperties(runner, "yellow", 1.0, "wood", "quack", "ACTIVE");
    }

    //Обнаружен баг - Некорректное значение поля height при значении поля material = «rubber» и нечетном id
    @Test(description = "Проверка вывода данных о уточке с нечетным id и значением поля material = rubber)")
    @CitrusTest
    public void propertiesDuckWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        getPropertiesDuck(runner, ODD_ID);
        validateResponseProperties(runner, "yellow", 1.0, "rubber", "quack", "ACTIVE");
    }
}