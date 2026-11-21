package tests.testdata;

import net.datafaker.Faker;

import java.util.UUID;

public class TestDataPersona {

    private final Faker faker = new Faker();

    public String personaName = faker.name().firstName();
    public String personaImage = "https://example.com/" + faker.internet().slug() + ".png";
    public String updatedPersonaName = faker.name().firstName();
    public String personaId = UUID.randomUUID().toString();
}
