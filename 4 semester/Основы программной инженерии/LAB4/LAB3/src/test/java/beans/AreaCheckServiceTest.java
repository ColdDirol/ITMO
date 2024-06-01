package beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AreaCheckServiceTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");

    @Test
    @DisplayName("Test check method with valid input")
    public void testCheckWithValidInput() {
        double x = 0;
        double y = 0;
        double r = 2;
        boolean result = AreaCheckService.check(x, y, r);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Test check method with invalid x value")
    public void testCheckWithInvalidXValue() {
        double x = 6;
        double y = 0;
        double r = 2;
        boolean result = AreaCheckService.check(x, y, r);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Test check method with invalid y value")
    public void testCheckWithInvalidYValue() {
        double x = 0;
        double y = 6;
        double r = 2;
        boolean result = AreaCheckService.check(x, y, r);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Test check method with invalid r value")
    public void testCheckWithInvalidRValue() {
        double x = 0;
        double y = 0;
        double r = 6;
        boolean result = AreaCheckService.check(x, y, r);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Test compiledDate method")
    public void testCompiledDate() {
        LocalDateTime ldt = LocalDateTime.now();
        String result = AreaCheckService.compiledDate(ldt);
        String expected = ldt.format(formatter);
        Assertions.assertEquals(expected, result);
    }
}