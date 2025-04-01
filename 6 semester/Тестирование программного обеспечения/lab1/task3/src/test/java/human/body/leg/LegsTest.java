package human.body.leg;

import org.junit.jupiter.api.Test;
import vladimir.human.body.leg.Legs;

import static org.junit.jupiter.api.Assertions.*;

class LegsTest {

    @Test
    void testLegsCreationValidParameters() {
        assertDoesNotThrow(() -> new Legs("Ноги", "Владелец"));
    }

    @Test
    void testLegsAssCreation() {
        Legs legs = new Legs("Ноги", "Владелец");
        assertNotNull(legs.getAss());
        assertEquals("Жопа", legs.getAss().getName());
    }
}
