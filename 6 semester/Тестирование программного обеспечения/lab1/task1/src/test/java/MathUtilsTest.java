import org.junit.jupiter.api.Test;
import vladimir.MathUtils;

import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {

    @Test
    public void testArccos() {
        double delta = 0.1;

        // Проверка на граничных значениях
        assertEquals(Math.PI / 2, MathUtils.arccos(0), delta);
        assertEquals(0, MathUtils.arccos(1), delta);
        assertEquals(Math.PI, MathUtils.arccos(-1), delta);

        // Проверка на промежуточных значениях
        assertEquals(Math.PI / 4, MathUtils.arccos(Math.sqrt(2) / 2), delta);
        assertEquals(Math.PI / 3, MathUtils.arccos(0.5), delta);
        assertEquals(Math.PI / 6, MathUtils.arccos(Math.sqrt(3) / 2), delta);

        // Проверка на исключение при недопустимых значениях
        assertThrows(IllegalArgumentException.class, () -> MathUtils.arccos(1.1));
        assertThrows(IllegalArgumentException.class, () -> MathUtils.arccos(-1.1));
    }
}