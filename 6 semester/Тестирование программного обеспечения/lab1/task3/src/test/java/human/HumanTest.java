package human;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vladimir.furniture.Armchair;
import vladimir.furniture.Chair;
import vladimir.human.Human;
import vladimir.human.body.BodyPart;
import vladimir.place.Room;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class HumanTest {
    private Human artur;
    private Human human;
    private Room room;
    private Armchair armchair;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        artur = new Human(
                "Артур",
                12,
                new BodyPart("Голова"),
                new BodyPart("Тело"),
                new BodyPart("Левая рука"),
                new BodyPart("Правая рука"),
                new BodyPart("Левая нога"),
                new BodyPart("Правая нога")
        );

        human = new Human(
                "Человек",
                3080,
                new BodyPart("Левая голова"),
                new BodyPart("Правая голова"),
                new BodyPart("Тело"),
                new BodyPart("Левая рука"),
                new BodyPart("Правая рука"),
                new BodyPart("Левая нога"),
                new BodyPart("Правая нога")
        );

        room = new Room();
        armchair = new Armchair();

        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testHumanInitialization() {
        assertEquals("Артур", artur.getName());
        assertEquals(12, artur.getAge());
        assertEquals(6, artur.getOrgans().size());
    }

    @Test
    void testFurnitureInitialization() {
        Chair chair = new Chair();
        assertEquals("Стоял стул\n", outContent.toString());
    }

    @Test
    void testBodyPartInitialization() {
        BodyPart hand = new BodyPart("Рука");
        assertEquals("Рука", hand.getName());
    }

    @Test
    void testPlaceInitialization() {
        assertEquals("Комната", room.getName());
    }

    @Test
    void testHumanActions() {
        artur.sit(armchair);
        artur.comeIn(room);
        artur.beNervous();
        artur.seeSmb(human);
        artur.notBelieve();
        artur.beSurprised();

        String expectedOutput = "Артур сидел в Кресло\n" +
                "Артур зашел в Комната\n" +
                "Артур нервничал\n" +
                "Артур увидел Человек\n" +
                "Артур не верил\n" +
                "Артур удивился\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testHumanMovement() {
        human.move();
        assertEquals(7, human.getOrgans().size());

        String expectedOutput = "Человек шевелит Левая голова\n" +
                "Человек шевелит Правая голова\n" +
                "Человек шевелит Тело\n" +
                "Человек шевелит Левая рука\n" +
                "Человек шевелит Правая рука\n" +
                "Человек шевелит Левая нога\n" +
                "Человек шевелит Правая нога\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testInvalidHumanCreation() {
        assertThrows(NullPointerException.class, () -> new Human(null, 25));
        assertThrows(IllegalArgumentException.class, () -> new Human("", 25));
        assertThrows(IllegalArgumentException.class, () -> new Human("Иван", -5));
    }
}