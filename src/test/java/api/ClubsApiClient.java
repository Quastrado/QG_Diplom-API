package api;

import io.qameta.allure.Step;
import models.clubs.*;
//import models.clubs.ClubResponse;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.clubs.ClubsSpec.*;

public class ClubsApiClient {

    @Step("[API] Get clubs list GET /clubs/")
    public ClubsListResponseModel getClubs() {
        return given(baseRequestSpec)
                .when()
                .get("/clubs/")
                .then()
                .spec(successfulClubsListResponseSpec)
                .extract()
                .as(ClubsListResponseModel.class);
    }

    @Step("[API] Get club GET /clubs/")
    public ClubResponseModel getClub(int id) {
        return given(baseRequestSpec)
                .when()
                .get("/clubs/" + id)
                .then()
                .spec(successGetResponseClubSpec)
                .extract()
                .as(ClubResponseModel.class);
    }

    @Step("[API] Create club POST /clubs/")
    public CreateClubResponseBodyModel createClub(CreateClubBodyModel body, String accessToken) {
        return given(baseRequestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .post("/clubs/")
                .then()
                .spec(successfulCreateClubResponseSpec)
                .extract()
                .as(CreateClubResponseBodyModel.class);
    }

    @Step("[API] Update club PATCH /clubs/{id}/")
    public UpdateClubResponseBodyModel updateClub(int id, UpdateClubRequestBodyModel body, String token) {
        return given(baseRequestSpec)
                .body(body)
                .when()
                .auth().oauth2(token)
                .patch("/clubs/" + id + "/")
                .then()
                .spec(successUpdateResponseClubSpec)
                .extract().as(UpdateClubResponseBodyModel.class);
    }

    @Step("[API] Delete club DELETE /clubs/{id}/")
    public void deleteClub(int id, String token) {
        given(baseRequestSpec)
                .when()
                .auth().oauth2(token)
                .delete("/clubs/" + id + "/")
                .then()
                .spec(successDeleteResponseClubSpec);
    }
}
