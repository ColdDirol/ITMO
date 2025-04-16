package unit;

import com.vladimir.log.Ln;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LnTest {

    private static final double DELTA = 0.1;
    private static final double eps = 0.0001;

    @ParameterizedTest
    @ValueSource(doubles = {1, 2, 3, 5, 10, 100})
    @DisplayName("Тестирование ln для положительных значений")
    public void testLnPositiveValues(double value) {
        assertEquals(Math.log(value), new Ln().ln(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.5, 0.9})
    @DisplayName("Тестирование ln для значений (0,1)")
    public void testLnBetweenZeroAndOne(double value) {
        assertEquals(Math.log(value), new Ln().ln(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0})
    @DisplayName("Тестирование ln для нуля (должен вернуть -Infinity)")
    public void testLnZero(double value) {
        assertEquals(Double.NEGATIVE_INFINITY, new Ln().ln(value, eps));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY})
    @DisplayName("Тестирование ln для +Infinity (должен вернуть +Infinity)")
    public void testLnPositiveInfinity(double value) {
        assertEquals(Double.POSITIVE_INFINITY, new Ln().ln(value, eps));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -10, -100, Double.NEGATIVE_INFINITY})
    @DisplayName("Тестирование ln для отрицательных значений (должен вернуть NaN)")
    public void testLnNegativeValues(double value) {
        assertTrue(Double.isNaN(new Ln().ln(value, eps)));
    }
}
