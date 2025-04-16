package unit;

import com.vladimir.log.Ln;
import com.vladimir.log.Log;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogTest {

    private static final double EPS = 1e-6;

    @ParameterizedTest
    @CsvSource({
            "2, 8, 3",     // log2(8) = 3
            "10, 100, 2",   // log10(100) = 2
            "5, 25, 2",     // log5(25) = 2
            "2, 0.5, -1",   // log2(0.5) = -1
            "10, 1, 0"      // log10(1) = 0
    })
    @DisplayName("Тестирование log с различными основаниями")
    public void testLog(double a, double b, double expected) {
        Log log = new Log();
        assertEquals(expected, log.log(a, b, EPS), 1e-6);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 0",    // log2(0)
            "10, -1",   // log10(-1)
            "1, 5",     // log1(5)
            "0, 5",     // log0(5)
            "-2, 8"     // log-2(8)
    })
    @DisplayName("Тестирование log с недопустимыми значениями")
    public void testLogInvalidCases(double a, double b) {
        Log log = new Log();
        assertTrue(Double.isNaN(log.log(a, b, EPS)) ||
                Double.isInfinite(log.log(a, b, EPS)));
    }

    @Test
    @DisplayName("Тестирование writeCSV")
    public void testWriteCSV() throws IOException {
        Log log = new Log();
        StringWriter writer = new StringWriter();
        double result = log.writeCSV(10, 100, EPS, writer);

        assertEquals(2.0, result, 1e-6);
        assertTrue(writer.toString().contains("100.0,2.0"));
    }

    @Test
    @DisplayName("Тестирование зависимости от Ln")
    public void testLogWithMockLn() {
        // Создаем mock для Ln, который всегда возвращает 1
        Ln mockLn = new Ln() {
            @Override
            public double ln(double x, double eps) {
                return 1.0;
            }
        };

        Log log = new Log(mockLn);
        assertEquals(1.0, log.log(10, 100, EPS), 1e-6);
    }
}
