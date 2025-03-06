import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import vladimir.MathUtils;

import java.util.Random;
import java.util.stream.DoubleStream;

import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {

    private static final double DELTA = 0.1;
    private static final Random random = new Random();

    @ParameterizedTest
    @ValueSource(doubles = {0})
    @DisplayName("Тестирует arccos для значения 0; ожидается результат PI/2.")
    public void testArccosBoundaryZero(double value) {
        assertEquals(Math.PI / 2, MathUtils.arccos(value), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1})
    @DisplayName("Тестирует arccos для значения 1; ожидается результат 0.")
    public void testArccosBoundaryOne(double value) {
        assertEquals(0, MathUtils.arccos(value), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1})
    @DisplayName("Тестирует arccos для значения -1; ожидается результат PI.")
    public void testArccosBoundaryNegativeOne(double value) {
        assertEquals(Math.PI, MathUtils.arccos(value), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.7071, 0.5, 0.866}) // sqrt(2)/2 = 0.7071, sqrt(1)/2 = 0.5, sqrt(3)/2 = 0.866
    @DisplayName("Тестирует arccos для промежуточных значений; ожидаются соответствующие результаты.")
    public void testArccosIntermediateValues(double value) {
        double expected;
        if (value == 0.7071) expected = Math.PI / 4;
        else if (value == 0.5) expected = Math.PI / 3;
        else expected = Math.PI / 6;

        assertEquals(expected, MathUtils.arccos(value), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.1, -1.1})
    @DisplayName("Тестирует arccos для значений вне допустимого диапазона; ожидается выброс IllegalArgumentException.")
    public void testArccosThrowsException(double value) {
        assertThrows(IllegalArgumentException.class, () -> MathUtils.arccos(value));
    }

    @ParameterizedTest
    @MethodSource("provideRandomValues")
    @DisplayName("Тестирует arccos для случайных значений в диапазоне [-1.1, 1.1].")
    public void testArccosRandomValues(double randomValue) {
        if (isValueValid(randomValue)) {
            double expected = Math.acos(randomValue);
            double actual = MathUtils.arccos(randomValue);
            assertEquals(expected, actual, DELTA);
        } else {
            assertThrows(IllegalArgumentException.class, () -> MathUtils.arccos(randomValue));
        }
    }

    private static Boolean isValueValid(double value) {
        return value >= -1 && value <= 1;
    }

    private static DoubleStream provideRandomValues() {
        return random.doubles(100, -1.1, 1.1);
    }
}