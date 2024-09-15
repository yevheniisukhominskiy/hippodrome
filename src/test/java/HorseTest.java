import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


class HorseTest {
    @Test
    public void constructor_NullFirstParameter_IllegalArgumentException() {
        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        Assertions.assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\t", "\t\t", "\n", "\n\n"})
    public void constructor_EmptyNameParameter_IllegalArgumentException(String name) {
        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        Assertions.assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void constructor_NegativeSecondParameter_IllegalArgumentException() {
        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", -1, 1));
        Assertions.assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void constructor_NegativeThirdParameter_IllegalArgumentException() {
        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", 1, -1));
        Assertions.assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName() {
        Horse horse = new Horse("Duichik", 1, 1);
        Assertions.assertEquals("Duichik", horse.getName());
    }

    @Test
    public void getSpeed() {
        Horse horse = new Horse("Duichik", 1, 1);
        Assertions.assertEquals(1, horse.getSpeed());
    }

    @Test
    public void getDistance_ReturnsThirdParameterValue() {
        Horse horse = new Horse("Duichik", 1, 1);
        Assertions.assertEquals(1, horse.getDistance());
    }

    @Test
    public void getDistance_ReturnsZeroWhenCreatedWithTwoParameters() {
        Horse horse = new Horse("Duichik", 1);
        Assertions.assertEquals(0, horse.getDistance());
    }

    @Test
    public void move_CallsGetRandomDoubleWithParameters()    {
        try(MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Duichik", 1, 1);
            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 0.7, 0.9})
    public void move_AssignsCorrectDistance(double randomValue) {
        try(MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Duichik", 1, 1);

            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            horse.move();

            double expectedDistance = 1 + 1 * randomValue;
            Assertions.assertEquals(expectedDistance, horse.getDistance());
        }
    }
}