package specs.persona;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;

public class PersonaSpec {

    private static final String UUID_REGEX =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static ResponseSpecification createPersonaSuccessSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesPattern(UUID_REGEX))
            .build();

    public static ResponseSpecification getPersonaSuccessSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/persona/get_persona_response_schema.json"))
            .expectBody("id", notNullValue())
            .build();

    public static ResponseSpecification updatePersonaSuccessSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesPattern(UUID_REGEX))
            .build();

    public static ResponseSpecification deletePersonaSuccessSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification personaConflictSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(409)
            .expectBody(matchesJsonSchemaInClasspath("schemas/common/error_response_schema.json"))
            .build();

    public static ResponseSpecification personaNotFoundSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(404)
            .expectBody(matchesJsonSchemaInClasspath("schemas/common/error_response_schema.json"))
            .build();
}
