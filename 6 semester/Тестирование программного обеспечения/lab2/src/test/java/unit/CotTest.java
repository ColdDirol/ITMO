package unit;

import com.vladimir.trig.Cot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CotTest {

    private static final double DELTA = 0.1;
    private static final double eps = 0.1;

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI/4, Math.PI/3, Math.PI/6})
    @DisplayName("Тестирует cot для основных значений")
    public void testCotBasicValues(double value) {
        double expected = 1.0 / Math.tan(value);
        assertEquals(expected, new Cot().cot(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, Math.PI, -Math.PI})
    @DisplayName("Тестирует cot для значений, где sin(x) = 0 (должен вернуть NaN)")
    public void testCotWhenSinZero(double value) {
        assertTrue(Double.isNaN(new Cot().cot(value, eps)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    @DisplayName("Тестирует cot для бесконечности (должен вернуть NaN)")
    public void testCotInfinity(double value) {
        assertTrue(Double.isNaN(new Cot().cot(value, eps)));
    }
}
