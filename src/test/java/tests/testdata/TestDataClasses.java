package tests.testdata;

import net.datafaker.Faker;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TestDataClasses {

    private static final Faker faker = new Faker();

    public String firstFilter = "hitDice";
    public static String existentClassUrl() {
        return faker.options().option("warlock", "bard", "monk", "rogue");
    }
    public static String nonExistentClassUrl() {
        return faker.options().option("druid", "cleric", "barbarian", "paladin");
    }
    public String nonExistentClassMessage(String nonExistentClassUrl){
        return "Класс с url " + nonExistentClassUrl + " не существует";
    }
    public static Stream<Arguments> existingClassesProvider() {
        return Stream.of(
                Arguments.of("warlock", "Колдун", "Warlock", "PACT"),
                Arguments.of("bard", "Бард", "Bard", "FULL"),
                Arguments.of("monk", "Монах", "Monk", "NONE"),
                Arguments.of("rogue", "Плут", "Rogue", "NONE")
        );
    }
    public String rusClassName = "Обновлённый " + faker.lorem().word();
    public String engClassName = "Updated " + faker.lorem().word();
}
