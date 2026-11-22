package specs.classes;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class ClassesSpec {

    public static ResponseSpecification getFiltersResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/classes/get_filters_response_schema.json"
            ))
            .expectBody("filters", notNullValue())
            .build();

    public static ResponseSpecification classNotFoundResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(404)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/common/error_response_schema.json"
            ))
            .expectBody("message", notNullValue())
            .build();

    public static ResponseSpecification getClassSuccessResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/classes/get_class_response_schema.json"
            ))
            .expectBody("url", notNullValue())
            .build();

    public static ResponseSpecification headClassSuccessResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification headClassNotFoundResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(404)
            .build();

    public static ResponseSpecification classForbiddenResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(403)
            .expectBody(matchesJsonSchemaInClasspath("schemas/common/error_response_schema.json"))
            .expectBody("message", notNullValue())
            .build();
}
