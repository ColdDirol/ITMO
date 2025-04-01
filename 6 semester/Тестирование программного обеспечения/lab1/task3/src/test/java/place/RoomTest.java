package place;

import org.junit.jupiter.api.Test;
import vladimir.place.Room;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomTest {

    @Test
    void testRoomCreation() {
        assertDoesNotThrow(() -> new Room());
    }

    @Test
    void testRoomName() {
        Room room = new Room();
        assertEquals("Комната", room.getName());
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