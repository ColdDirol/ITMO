package human;

import org.junit.jupiter.api.Test;
import vladimir.human.Human;
import vladimir.human.body.BodyPartAbstract;
import vladimir.human.body.head.Head;
import vladimir.place.Place;
import vladimir.place.Room;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class HumanTest {

    @Test
    void testHumanCreationValidParameters() {
        assertDoesNotThrow(() -> new Human("Иван", 25));
    }

    @Test
    void testHumanCreationInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> new Human("", 25));
    }

    @Test
    void testHumanCreationInvalidAge() {
        assertThrows(IllegalArgumentException.class, () -> new Human("Иван", -1));
    }

    @Test
    void testComeIn() {
        Human human = new Human("Иван", 25);
        Place place = new Room();
        String expectedOutput = "Иван зашел в Комната";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        human.comeIn(place);

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testMove() {
        Human human = new Human("Иван", 25);
        BodyPartAbstract bodyPart = new Head("Голова", "Иван");
        String expectedOutput = "Иван шевелит Голова";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        human.move(bodyPart);

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }
}
