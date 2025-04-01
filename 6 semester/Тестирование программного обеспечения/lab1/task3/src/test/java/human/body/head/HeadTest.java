package human.body.head;

import org.junit.jupiter.api.Test;
import vladimir.human.body.head.Head;
import vladimir.human.emotion.EmotionLevel;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HeadTest {

    @Test
    void testHeadCreationValidParameters() {
        assertDoesNotThrow(() -> new Head("Голова", "Владелец"));
    }

    @Test
    void testHeadSmileWithLevel() {
        Head head = new Head("Голова", "Владелец");
        String expectedOutput = "Голова Владелец широко улыбнулся";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        head.smileWithLevel(EmotionLevel.HIGH);

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testHeadBeSurprisedWithLevel() {
        Head head = new Head("Голова", "Владелец");
        String expectedOutput = "Владелец удивился";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        head.beSurprised(EmotionLevel.MEDIUM);

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testHeadBeNervousWithLevel() {
        Head head = new Head("Голова", "Владелец");
        String expectedOutput = "Владелец сильно нервничал";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        head.beNervous(EmotionLevel.HIGH);

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void testHeadNotBelieveInOwnSmb() {
        Head head = new Head("Голова", "Владелец");
        String expectedOutput = "Владелец не верил своему что-то";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        head.notBelieveInOwnSmb("что-то");

        System.setOut(originalOut);

        assertEquals(expectedOutput, outputStream.toString().trim());
    }
}