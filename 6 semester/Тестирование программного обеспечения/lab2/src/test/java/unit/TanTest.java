package unit;

import com.vladimir.trig.Tan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TanTest {

    private static final double DELTA = 0.1;
    private static final double eps = 0.1;

    @ParameterizedTest
    @ValueSource(doubles = {0, Math.PI, -Math.PI, 2*Math.PI})
    @DisplayName("Тестирует tan в точках, где sin(x)=0 (ожидается 0)")
    public void testTanWhereSinZero(double value) {
        assertEquals(0.0, new Tan().tan(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI/4, -Math.PI/4, 5*Math.PI/4})
    @DisplayName("Тестирует tan в точках, где tan(x)=1 или -1")
    public void testTanAtOneAndMinusOne(double value) {
        double expected = Math.tan(value);
        assertEquals(expected, new Tan().tan(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI/3, -Math.PI/3, 4*Math.PI/3})
    @DisplayName("Тестирует tan для промежуточных значений")
    public void testTanIntermediateValues(double value) {
        double expected = Math.tan(value);
        assertEquals(expected, new Tan().tan(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    @DisplayName("Тестирует tan для бесконечности (ожидается NaN)")
    public void testTanAtInfinity(double value) {
        assertTrue(Double.isNaN(new Tan().tan(value, eps)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.5, 1.0})
    @DisplayName("Тестирует tan для малых положительных значений")
    public void testTanSmallPositiveValues(double value) {
        assertEquals(Math.tan(value), new Tan().tan(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -0.5, -1.0})
    @DisplayName("Тестирует tan для малых отрицательных значений")
    public void testTanSmallNegativeValues(double value) {
        assertEquals(Math.tan(value), new Tan().tan(value, eps), DELTA);
    }
}
