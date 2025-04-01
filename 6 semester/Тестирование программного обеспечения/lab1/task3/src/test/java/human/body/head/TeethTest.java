package human.body.head;

import org.junit.jupiter.api.Test;
import vladimir.human.body.head.Teeth;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TeethTest {

    @Test
    void testTeethCreationValidParameters() {
        assertDoesNotThrow(() -> new Teeth("Зубы", "Часть тела", "Владелец"));
    }

    @Test
    void testTeethTouchWithHands() {
        Teeth teeth = new Teeth("Зубы", "Часть тела", "Владелец");
        String expectedOutput = "Владелеца ковырялся в Зубы Часть тела руками";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        teeth.touchWithHands();

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }
}