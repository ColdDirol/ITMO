package unit;

import com.vladimir.trig.Sin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SinTest {

    private static final double DELTA = 0.1;
    private static final double eps = 0.001;

    @ParameterizedTest
    @ValueSource(doubles = {0, Math.PI, -Math.PI, 2*Math.PI})
    @DisplayName("Тестирует sin для нулевых значений (ожидается 0)")
    public void testSinAtZeroCrossings(double value) {
        assertEquals(0.0, new Sin().sin(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI/4, Math.PI/3, Math.PI/6})
    @DisplayName("Тестирует sin для промежуточных значений")
    public void testSinIntermediateValues(double value) {
        double expected = Math.sin(value);
        assertEquals(expected, new Sin().sin(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    @DisplayName("Тестирует sin для бесконечности (ожидается NaN)")
    public void testSinAtInfinity(double value) {
        assertTrue(Double.isNaN(new Sin().sin(value, eps)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.5, 1.0, 1.5})
    @DisplayName("Тестирует sin для малых положительных значений")
    public void testSinSmallPositiveValues(double value) {
        assertEquals(Math.sin(value), new Sin().sin(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -0.5, -1.0, -1.5})
    @DisplayName("Тестирует sin для малых отрицательных значений")
    public void testSinSmallNegativeValues(double value) {
        assertEquals(Math.sin(value), new Sin().sin(value, eps), DELTA);
    }
}
