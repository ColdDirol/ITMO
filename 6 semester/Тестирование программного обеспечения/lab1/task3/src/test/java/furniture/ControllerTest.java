package furniture;

import org.junit.jupiter.api.Test;
import vladimir.furniture.Controller;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void testControllerCreationValidName() {
        assertDoesNotThrow(() -> new Controller("Контроллер"));
    }

    @Test
    void testControllerCreationNullName() {
        assertThrows(NullPointerException.class, () -> new Controller(null));
    }

    @Test
    void testControllerCreationEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new Controller(" "));
    }

    @Test
    void testControllerName() {
        Controller controller = new Controller("Контроллер");
        assertEquals("Контроллер", controller.getName());
    }
}
