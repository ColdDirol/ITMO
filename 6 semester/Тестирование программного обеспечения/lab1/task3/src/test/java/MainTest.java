import org.junit.jupiter.api.Test;
import vladimir.Main;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainTest {

    @Test
    public void testMainMethod() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }
}
