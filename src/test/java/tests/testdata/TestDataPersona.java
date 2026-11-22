package tests.testdata;

import net.datafaker.Faker;

import java.util.List;
import java.util.UUID;

public class TestDataPersona {

    private final Faker faker = new Faker();

    public String personaName = faker.name().firstName();
    public String personaImage = "https://example.com/" + faker.internet().slug() + ".png";
    public String updatedPersonaName = faker.name().firstName();
    public String personaId = UUID.randomUUID().toString();
    public String invalidPersonaId = faker.internet().slug();
    public static List<String> invalidUuidList() {
        return List.of(
                "invalid-uuid",
                "12345",
                "a3522348-b7ff-4356"
        );
    }
}
