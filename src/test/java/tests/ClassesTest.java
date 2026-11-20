package tests;

import models.common.ErrorResponseModel;
import models.classes.FiltersResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}