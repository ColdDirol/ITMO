package unit;

import com.vladimir.trig.Cos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CosTest {

    private static final double DELTA = 0.1;
    private static final double eps = 0.1;

    @ParameterizedTest
    @ValueSource(doubles = {0})
    @DisplayName("Тестирует cos для значения 0; ожидается результат 1.")
    public void testCosBoundaryZero(double value) {
        assertEquals(1, new Cos().cos(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI})
    @DisplayName("Тестирует cos для значения PI; ожидается результат -1.")
    public void testCosBoundaryPi(double value) {
        assertEquals(-1, new Cos().cos(value, eps), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI/4, Math.PI/3, Math.PI/6})
    @DisplayName("Тестирует cos для промежуточных значений; ожидаются соответствующие результаты.")
    public void testCosIntermediateValues(double value) {
        double expected = Math.cos(value);
        assertEquals(expected, new Cos().cos(value, eps), DELTA);
    }

}