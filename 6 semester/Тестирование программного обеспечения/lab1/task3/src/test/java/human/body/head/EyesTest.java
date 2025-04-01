package human.body.head;

import org.junit.jupiter.api.Test;
import vladimir.human.body.head.Eyes;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class EyesTest {

    @Test
    void testEyesCreationValidParameters() {
        assertDoesNotThrow(() -> new Eyes("Глаза", "Часть тела", "Владелец"));
    }

    @Test
    void testEyesSeeSmb() {
        Eyes eyes = new Eyes("Глаза", "Часть тела", "Владелец");
        String expectedOutput = "Часть тела Владелеца увидел Другойа";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        eyes.seeSmb("Другой");

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testEyesSeeSmbWithInvalidInput() {
        Eyes eyes = new Eyes("Глаза", "Часть тела", "Владелец");
        assertThrows(IllegalArgumentException.class, () -> eyes.seeSmb(""));
    }
}
