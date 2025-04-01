package human.body;

import org.junit.jupiter.api.Test;
import vladimir.human.body.BodyPartAbstract;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BodyPartAbstractTest {

    @Test
    void testBodyPartAbstractCreationValidParameters() {
        assertDoesNotThrow(() -> new BodyPartAbstract("Часть тела", "Владелец") {});
    }

    @Test
    void testMove() {
        BodyPartAbstract bodyPart = new BodyPartAbstract("Рука", "Владелец") {};
        String expectedOutput = "Владелец шевелит Рука";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        bodyPart.move();

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testPutOn() {
        BodyPartAbstract bodyPart = new BodyPartAbstract("Рука", "Владелец") {};
        String expectedOutput = "Владелец положил Рука на стол";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        bodyPart.putOn("стол");

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }
}
