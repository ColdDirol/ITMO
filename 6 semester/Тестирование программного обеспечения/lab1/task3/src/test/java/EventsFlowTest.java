import org.junit.jupiter.api.Test;
import vladimir.EventsFlow;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EventsFlowTest {

    @Test
    void testStory() {
        assertDoesNotThrow(() -> new EventsFlow().story());
    }
}