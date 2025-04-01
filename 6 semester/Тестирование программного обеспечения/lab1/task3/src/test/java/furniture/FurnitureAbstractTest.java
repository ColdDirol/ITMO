package furniture;

import org.junit.jupiter.api.Test;
import vladimir.furniture.Armchair;
import vladimir.furniture.FurnitureAbstract;

import static org.junit.jupiter.api.Assertions.*;

class FurnitureAbstractTest {

    @Test
    void testFurnitureAbstractCreationValidName() {
        assertDoesNotThrow(() -> new Armchair("Мебель"));
    }

    @Test
    void testFurnitureAbstractCreationNullName() {
        assertThrows(NullPointerException.class, () -> new Armchair(null));
    }

    @Test
    void testFurnitureAbstractCreationEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Armchair(" "));
    }

    @Test
    void testFurnitureAbstractName() {
        FurnitureAbstract furniture = new Armchair("Мебель");
        assertEquals("Мебель", furniture.getName());
    }
}
