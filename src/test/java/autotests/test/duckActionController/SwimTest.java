package autotests.test.duckActionController;

import autotests.clients.duckActionController.SwimClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class SwimTest extends SwimClient {
    //Обнаружен баг - Отсутствие атрибута соответствующего характеристики плаванья уточки
    @Test(description = "Проверка умения плавать уточки с существующим id")
    @CitrusTest
    public void swimDuckWithValidId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckBase(runner, "yellow", 1, "wood", "quack", "ACTIVE");
        getSwimDuck(runner, "${duckId}");
        validateResponseSwim(runner, HttpStatus.OK, "I'm swimming");
        deleteDuck(runner, "${duckId}");
    }

    //Обнаружен баг - Неверный json-ответ при проверке умения плавать несуществующей уточки
    @Test(description = "Проверка умения плавать уточки с несуществующим id")
    @CitrusTest
    public void swimDuckWithInvalidId(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckBase(runner, "yellow", 1, "rubber", "quack", "ACTIVE");
        deleteDuck(runner, "${duckId}");
        getSwimDuck(runner, "${duckId}");
        validateResponseSwim(runner, HttpStatus.NOT_FOUND, "Not Found");
    }
}