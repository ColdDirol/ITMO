package unit;

import com.vladimir.other.Pow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowTest {

    @ParameterizedTest
    @CsvSource({
            "2, 3, 8",
            "3, 4, 81",
            "5, 0, 1",
            "10, 1, 10",
            "2, -2, 0.25",
            "2, 10, 1024",
            "1.5, 2, 2.25",
            "0.5, 3, 0.125"
    })
    @DisplayName("Тестирование pow с различными основаниями и показателями")
    public void testPow(double base, int exponent, double expected) {
        assertEquals(expected, Pow.pow(base, exponent), 1e-6);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 5, 0",
            "0, 0, 1",  // 0^0 по соглашению
            "1, 100, 1"
    })
    @DisplayName("Тестирование pow с граничными значениями")
    public void testPowBoundaryCases(double base, int exponent, double expected) {
        assertEquals(expected, Pow.pow(base, exponent), 1e-6);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 31",
            "3, 20",
            "1.1, 100"
    })
    @DisplayName("Тестирование pow с большими показателями")
    public void testPowWithLargeExponents(double base, int exponent) {
        assertEquals(Math.pow(base, exponent), Pow.pow(base, exponent), 1e-6);
    }
}
