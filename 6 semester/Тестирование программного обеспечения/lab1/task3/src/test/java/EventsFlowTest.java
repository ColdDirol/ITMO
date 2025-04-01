import org.junit.jupiter.api.Test;
import vladimir.EventsFlow;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class EventsFlowTest {

    @Test
    public void testStoryMethod() {
        assertDoesNotThrow(() -> {
            EventsFlow eventsFlow = new EventsFlow();
            eventsFlow.story();
        });
    }
}
