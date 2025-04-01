package human.body;

import org.junit.jupiter.api.Test;
import vladimir.human.body.Body;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BodyTest {

    @Test
    void testBodyCreationValidParameters() {
        assertDoesNotThrow(() -> new Body("Тело", "Владелец"));
    }
}
