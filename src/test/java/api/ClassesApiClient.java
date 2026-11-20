package api;

import io.qameta.allure.Step;
import models.classes.ClassResponseModel;
import models.common.ErrorResponseModel;
import models.classes.FiltersResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.classes.ClassesSpec.*;

public class ClassesApiClient {

    @Step("[API] Получение метаданных фильтров GET /classes/filters")
    public FiltersResponseModel getFilters() {
        return given(baseRequestSpec)
                .when()
                .get("/classes/filters")
                .then()
                .spec(getFiltersResponseSpec)
                .extract().as(FiltersResponseModel.class);
    }

    @Step("[API] Запрос несуществующего класса GET /classes/{url}")
    public ErrorResponseModel getNonExistentClass(String url) {
        return given(baseRequestSpec)
                .pathParam("url", url)
                .when()
                .get("/classes/{url}")
                .then()
                .spec(classNotFoundResponseSpec)
                .extract().as(ErrorResponseModel.class);
    }

    @Step("[API] Получение класса по url GET /classes/{url}")
    public ClassResponseModel getClassByUrl(String url) {
        return given(baseRequestSpec)
                .pathParam("url", url)
                .when()
                .get("/classes/{url}")
                .then()
                .spec(getClassSuccessResponseSpec)
                .extract().as(ClassResponseModel.class);
    }

    @Step("[API] Проверка существования класса HEAD /classes/{url}")
    public void headClassByUrl(String url, boolean shouldExist) {
        given(baseRequestSpec)
                .pathParam("url", url)
                .when()
                .head("/classes/{url}")
                .then()
                .spec(shouldExist ? headClassSuccessResponseSpec : headClassNotFoundResponseSpec);
    }
}
