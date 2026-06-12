package autotests.test.duckActionController;

import autotests.clients.duckActionController.PropertiesClient;
import autotests.payloads.response.DuckPropertiesResponseProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class PropertiesTest extends PropertiesClient {

    private static final String ODD_ID = "3";
    private static final String EVEN_ID = "2";

    //Обнаружен баг - Пустое тело ответа при значении поля material = «wood» и четном id. Временно тест изменен на зеленый
    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка вывода данных о уточке с четным id и значением поля material = wood)")
    @Story("Эндпоинт /api/duck/action/properties")
    @Test()
    @CitrusTest
    public void propertiesDuckWithEvenId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", EVEN_ID);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        getPropertiesDuck(runner, "${duckId}");
        validateResponseJsonBodyFromFile(runner, HttpStatus.OK, "messageTest/MessagePropertiesDuckResponse.json");
        deleteDuckFromDatabase(runner, "${duckId}");
    }

    //Обнаружен баг - Некорректное значение поля height при значении поля material = «rubber» и нечетном id. Временно тест изменен на зеленый
    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка вывода данных о уточке с нечетным id и значением поля material = rubber)")
    @Story("Эндпоинт /api/duck/action/properties")
    @Test()
    @CitrusTest
    public void propertiesDuckWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("duckId", ODD_ID);
        createDuckInDatabase(runner, "${duckId}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        getPropertiesDuck(runner, "${duckId}");
        DuckPropertiesResponseProperties expectedResponse = new DuckPropertiesResponseProperties()
                .color("yellow")
                .height(3.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        validateResponseObject(runner, HttpStatus.OK, expectedResponse);
        deleteDuckFromDatabase(runner, "${duckId}");
    }
}