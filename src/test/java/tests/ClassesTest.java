package tests;

import models.classes.ClassResponseModel;
import models.common.ErrorResponseModel;
import models.classes.FiltersResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassesTest extends TestBase {

    @Test
    @DisplayName("Получение метаданных фильтров классов")
    public void getClassesFiltersTest() {
        FiltersResponseModel response = api.classes.getFilters();

        step("Проверка списка фильтров", () -> {
            assertThat(response.getFilters()).isNotEmpty();
            assertThat(response.getFilters().get(0).getKey()).isEqualTo("hitDice");
            assertThat(response.getFilters().get(0).getValues()).isNotEmpty();
        });
    }

    @Test
    @DisplayName("Запрос несуществующего класса возвращает 404")
    public void getNonExistentClassTest() {
        String nonExistentUrl = "bard-phb";
        ErrorResponseModel response = api.classes.getNonExistentClass(nonExistentUrl);

        step("Проверка текста ошибки 404", () -> {
            assertThat(response.getStatus()).isEqualTo(404);
            assertThat(response.getError()).isEqualTo("Not Found");
            assertThat(response.getMessage())
                    .isEqualTo("Класс с url " + nonExistentUrl + " не существует");
        });
    }

    @ParameterizedTest(name = "Получение карточки класса {0} ({1})")
    @CsvSource({
            "warlock, Колдун, Warlock, PACT",
            "bard, Бард, Bard, FULL",
            "monk, Монах, Monk, NONE",
            "rogue, Плут, Rogue, NONE"
    })
    @DisplayName("Успешное получение карточки существующего класса")
    public void getExistingClassTest(String url, String expectedRusName, String expectedEngName, String expectedCasterType) {
        ClassResponseModel response = api.classes.getClassByUrl(url);

        step("Проверка данных карточки класса", () -> {
            assertThat(response.getUrl()).isEqualTo(url);
            assertThat(response.getName().getRus()).isEqualTo(expectedRusName);
            assertThat(response.getName().getEng()).isEqualTo(expectedEngName);
            assertThat(response.getCasterType()).isEqualTo(expectedCasterType);
            assertThat(response.getHasSubclasses()).isTrue();
        });
    }

    @Test
    @DisplayName("HEAD запрос подтверждает наличие существующего класса (200)")
    public void headExistingClassTest() {
        api.classes.headClassByUrl("warlock", true);
    }

    @Test
    @DisplayName("HEAD запрос для несуществующего класса возвращает 404")
    public void headNonExistentClassTest() {
        api.classes.headClassByUrl("unknown-class", false);
    }
}