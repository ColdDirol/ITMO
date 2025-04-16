package unit;

import com.vladimir.trig.Sec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecTest {

    private static final double DELTA = 0.1;
    private static final double eps = 0.1;

    @ParameterizedTest
    @ValueSource(doubles = {0, 2*Math.PI, -2*Math.PI})
    @DisplayName("Тестирует sec для значений, где cos(x)=1 (ожидается 1)")
    public void testSecWhereCosOne(double value) {
        assertEquals(1.0, new Sec().sec(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI, -Math.PI, 3*Math.PI})
    @DisplayName("Тестирует sec для значений, где cos(x)=-1 (ожидается -1)")
    public void testSecWhereCosMinusOne(double value) {
        assertEquals(-1.0, new Sec().sec(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI/4, Math.PI/3, Math.PI/6})
    @DisplayName("Тестирует sec для промежуточных значений")
    public void testSecIntermediateValues(double value) {
        double expected = 1.0 / Math.cos(value);
        assertEquals(expected, new Sec().sec(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    @DisplayName("Тестирует sec для бесконечности (ожидается NaN)")
    public void testSecInfinity(double value) {
        assertTrue(Double.isNaN(new Sec().sec(value, eps)));
    }
}
