package tests.testdata;

import net.datafaker.Faker;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class TestDataClasses {

    public String firstFilter = "hitDice";
    public static String existentClassUrl() {
        return new Faker().options().option("warlock", "bard", "monk", "rogue");
    }
    public static String nonExistentClassUrl() {
        return new Faker().options().option("druid", "cleric", "barbarian", "paladin");
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

}
