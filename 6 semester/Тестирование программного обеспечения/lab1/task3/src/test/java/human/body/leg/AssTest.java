package human.body.leg;

import org.junit.jupiter.api.Test;
import vladimir.human.body.leg.Ass;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AssTest {

    @Test
    void testAssCreationValidParameters() {
        assertDoesNotThrow(() -> new Ass("Зад", "Нижняя часть тела", "Владелец"));
    }

    @Test
    void testAssSitTo() {
        Ass ass = new Ass("Зад", "Нижняя часть тела", "Владелец");
        String expectedOutput = "Зад Владелеца сидел на стуле";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        ass.sitTo("стуле");

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }
}
