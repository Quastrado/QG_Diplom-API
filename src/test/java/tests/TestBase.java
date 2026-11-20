package tests;

import api.ApiClient;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    protected final ApiClient api = new ApiClient();

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = System.getProperty("baseUrl", "http://localhost:8080");
        RestAssured.basePath = "/api/v2";
    }
}
