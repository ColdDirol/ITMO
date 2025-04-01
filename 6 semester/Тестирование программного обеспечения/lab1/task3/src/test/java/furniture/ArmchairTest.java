package furniture;

import org.junit.jupiter.api.Test;
import vladimir.furniture.Armchair;

import static org.junit.jupiter.api.Assertions.*;

class ArmchairTest {

    @Test
    void testArmchairCreationValidName() {
        assertDoesNotThrow(() -> new Armchair("Удобное кресло"));
    }

    @Test
    void testArmchairCreationNullName() {
        assertThrows(NullPointerException.class, () -> new Armchair(null));
    }

    @Test
    void testArmchairCreationEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Armchair(" "));
    }

    @Test
    void testArmchairName() {
        Armchair armchair = new Armchair("Удобное кресло");
        assertEquals("Удобное кресло", armchair.getName());
    }
}