package specs;

import allure.CustomAllureListener;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.filter.log.LogDetail.ALL;

public class BaseSpec {

    public static RequestSpecification baseRequestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .setContentType(ContentType.JSON)
            .addFilter(CustomAllureListener.withCustomTemplate())
            .log(ALL)
            .build();
}
