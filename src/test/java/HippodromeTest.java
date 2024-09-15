import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class HippodromeTest {

    @Test
    public void constructor_NullParameter_IllegalArgumentException() {
        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        Assertions.assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void constructor_EmptyListParameter_IllegalArgumentException() {
        var exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        Assertions.assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorses() {
        var list = new ArrayList<Horse>();
        for (int i = 0; i < 30; i++) {
            list.add(new Horse("Horse "  + i, i, i * 3));
        }

        Hippodrome hippodrome = new Hippodrome(list);
        var res = hippodrome.getHorses();
        Assertions.assertEquals(list, res);
    }

    @Test
    public void move() {
        var list = new ArrayList<Horse>();
        for (int i = 0; i < 50; i++) {
            Horse mockitoHorse = Mockito.mock(Horse.class);
            list.add(mockitoHorse);
        }

        Hippodrome hippodrome = new Hippodrome(list);
        hippodrome.move();
        for (Horse horse : list) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinner() {
        Horse horse1 = new Horse("Horse1"  + 1, 10, 14);
        Horse horse2 = new Horse("Horse2"  + 1, 10, 19);
        Horse horse3 = new Horse("Horse3"  + 1, 10, 21);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));
        Assertions.assertEquals(horse3, hippodrome.getWinner());
    }
}