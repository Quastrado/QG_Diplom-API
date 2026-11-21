package api;

import io.qameta.allure.Step;
import models.common.ErrorResponseModel;
import models.persona.PersonaRequestModel;
import models.persona.PersonaResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.persona.PersonaSpec.*;

public class PersonaApiClient {

    @Step("[API] Создание персоны POST /persona")
    public String createPersona(PersonaRequestModel body) {
        return given(baseRequestSpec)
                .body(body)
                .when()
                .post("/persona")
                .then()
                .spec(createPersonaSuccessSpec)
                .extract().asString();
    }

    @Step("[API] Создание персоны с конфликтом версии POST /persona (409)")
    public ErrorResponseModel createPersonaWithConflict(PersonaRequestModel body) {
        return given(baseRequestSpec)
                .body(body)
                .when()
                .post("/persona")
                .then()
                .spec(personaConflictSpec)
                .extract().as(ErrorResponseModel.class);
    }

    @Step("[API] Получение персоны по ID GET /persona/{id}")
    public PersonaResponseModel getPersonaById(String id) {
        return given(baseRequestSpec)
                .pathParam("id", id)
                .when()
                .get("/persona/{id}")
                .then()
                .spec(getPersonaSuccessSpec)
                .extract().as(PersonaResponseModel.class);
    }

    @Step("[API] Запрос несуществующей персоны GET /persona/{id} (404)")
    public ErrorResponseModel getNonExistentPersona(String id) {
        return given(baseRequestSpec)
                .pathParam("id", id)
                .when()
                .get("/persona/{id}")
                .then()
                .spec(personaNotFoundSpec)
                .extract().as(ErrorResponseModel.class);
    }

    @Step("[API] Частичное обновление персоны PATCH /persona/{id}")
    public void patchPersona(String id, PersonaRequestModel body) {
        given(baseRequestSpec)
                .pathParam("id", id)
                .body(body)
                .when()
                .patch("/persona/{id}")
                .then()
                .spec(updatePersonaSuccessSpec);
    }

    @Step("[API] Удаление персоны DELETE /persona/{id}")
    public void deletePersona(String id) {
        given(baseRequestSpec)
                .pathParam("id", id)
                .when()
                .delete("/persona/{id}")
                .then()
                .spec(deletePersonaSuccessSpec);
    }
}
