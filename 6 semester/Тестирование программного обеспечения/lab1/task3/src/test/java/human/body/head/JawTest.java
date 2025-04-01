package human.body.head;

import org.junit.jupiter.api.Test;
import vladimir.human.body.head.Jaw;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JawTest {

    @Test
    void testJawCreationValidParameters() {
        assertDoesNotThrow(() -> new Jaw("Челюсть", "Часть тела", "Владелец"));
    }

    @Test
    void testJawDropping() {
        Jaw jaw = new Jaw("Челюсть", "Голова", "Владелец");
        String expectedOutput = "Челюсть отвисла";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        jaw.dropping();

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }
}