package place;

import org.junit.jupiter.api.Test;
import vladimir.place.Place;
import vladimir.place.Room;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PlaceTest {

    @Test
    void testPlaceCreationValidName() {
        assertDoesNotThrow(Room::new);
    }

    @Test
    void testPlaceCreationNullName() {
        assertThrows(NullPointerException.class, () -> new Place(null) {});
    }

    @Test
    void testPlaceCreationEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Place(" ") {});
    }

    @Test
    void testConsoleOutput() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        new Room();

        System.setOut(originalOut);

        String expectedOutput = "Существовало место - Комната\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}