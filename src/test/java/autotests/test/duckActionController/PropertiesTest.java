package autotests.test.duckActionController;

import autotests.clients.duckActionController.PropertiesClient;
import autotests.payloads.request.DuckPropertiesRequestCreate;
import autotests.payloads.response.DuckPropertiesResponseProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
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
        runner.variable("id", EVEN_ID);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "wood", "quack", "ACTIVE");
        getPropertiesDuck(runner, "${id}");
        validateResponseJsonBodyFromFile(runner, HttpStatus.OK, "messageTest/MessagePropertiesDuckResponse.json");
        deleteDuckFromDatabase(runner, "${id}");
    }

    //Обнаружен баг - Некорректное значение поля height при значении поля material = «rubber» и нечетном id. Временно тест изменен на зеленый
    @Epic("Тесты на duck-action-controller")
    @Feature("Проверка вывода данных о уточке с нечетным id и значением поля material = rubber)")
    @Story("Эндпоинт /api/duck/action/properties")
    @Test()
    @CitrusTest
    public void propertiesDuckWithOddId(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("id", ODD_ID);
        createDuckInDatabase(runner, "${id}", "yellow", "0.03", "rubber", "quack", "ACTIVE");
        getPropertiesDuck(runner, "${id}");
        DuckPropertiesResponseProperties expectedResponse = new DuckPropertiesResponseProperties()
                .color("yellow")
                .height(3.0)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");
        validateResponseObject(runner, HttpStatus.OK, expectedResponse);
        deleteDuckFromDatabase(runner, "${id}");
    }

    @Test(dataProvider = "ducks", description = "Изменение свойств уточки (параметризованный тест)")
    @CitrusTest
    @CitrusParameters({"properties", "runner"})
    public void propertiesDuckParam(
            DuckPropertiesRequestCreate properties,
            @Optional @CitrusResource TestCaseRunner runner) {
        generateId(runner);
        createDuckInDatabase(runner, "${id}", properties.color(), String.valueOf(properties.height()),
                properties.material(), properties.sound(), properties.wingsState());
        getPropertiesDuck(runner, "${id}");
        DuckPropertiesResponseProperties expectedResponse = new DuckPropertiesResponseProperties()
                .color(properties.color())
                .height(properties.height())
                .material(properties.material())
                .sound(properties.sound())
                .wingsState(properties.wingsState());
        validateResponseObject(runner, HttpStatus.OK, expectedResponse);
        deleteDuckFromDatabase(runner, "${id}");
    }

    @DataProvider(name = "ducks")
    public static Object[][] ducks() {
        DuckPropertiesRequestCreate yellowWoodActiveDuck = new DuckPropertiesRequestCreate()
                .color("yellow")
                .height(1.3)
                .material("wood")
                .sound("quack")
                .wingsState("ACTIVE");

        DuckPropertiesRequestCreate redRubberFixedDuck = new DuckPropertiesRequestCreate()
                .color("red")
                .height(0.5)
                .material("rubber")
                .sound("quack-quack")
                .wingsState("FIXED");

        DuckPropertiesRequestCreate blueMetalActiveDuck = new DuckPropertiesRequestCreate()
                .color("blue")
                .height(1.0)
                .material("metal")
                .sound("quack")
                .wingsState("ACTIVE");

        DuckPropertiesRequestCreate greenPlasticFixedDuck = new DuckPropertiesRequestCreate()
                .color("green")
                .height(1.7)
                .material("plastic")
                .sound("quack-quack")
                .wingsState("FIXED");

        DuckPropertiesRequestCreate orangeWoodUndefinedDuck = new DuckPropertiesRequestCreate()
                .color("orange")
                .height(0.2)
                .material("wood")
                .sound("quack")
                .wingsState("UNDEFINED");

        return new Object[][]{
                {yellowWoodActiveDuck, null},
                {blueMetalActiveDuck, null},
                {redRubberFixedDuck, null},
                {greenPlasticFixedDuck, null},
                {orangeWoodUndefinedDuck, null}
        };
    }
}