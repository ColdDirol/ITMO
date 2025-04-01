package human.body;

import org.junit.jupiter.api.Test;
import vladimir.human.body.Hands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HandsTest {

    @Test
    void testHandsCreationValidParameters() {
        assertDoesNotThrow(() -> new Hands("Руки", "Владелец"));
    }
}