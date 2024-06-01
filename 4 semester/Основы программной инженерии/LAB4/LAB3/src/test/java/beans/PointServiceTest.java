package beans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointsServiceTest {

    private PointsService pointsService;

    @BeforeEach
    public void setUp() {
        pointsService = new PointsService();
    }

    @Test
    public void testFormatDouble() {
        Double number = 1.23456;
        Double formattedNumber = pointsService.formatDouble(number);
        assertEquals(1.23, formattedNumber);
    }
}