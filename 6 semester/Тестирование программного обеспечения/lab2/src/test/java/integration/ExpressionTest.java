package integration;

import com.vladimir.*;
import com.vladimir.log.Ln;
import com.vladimir.log.Log;
import com.vladimir.trig.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionTest {

    private static final double DELTA = 0.1;
    private static final double funcEps = 0.1;
    private static final double eps = 0.1;

    private static Sin sinMock;
    private static Cos cosMock;
    private static Sec secMock;
    private static Tan tanMock;
    private static Cot cotMock;

    private static Ln lnMock;
    private static Log logMock;

    private static Reader sinIn;
    private static Reader cosIn;
    private static Reader secIn;
    private static Reader tanIn;
    private static Reader cotIn;
    private static Reader lnIn;
    private static Reader log2In;
    private static Reader log5In;
    private static Reader log10In;

    @BeforeAll
    static void setUpBeforeTest() {
        sinMock = Mockito.mock(Sin.class);
        cosMock = Mockito.mock(Cos.class);
        secMock = Mockito.mock(Sec.class);
        tanMock = Mockito.mock(Tan.class);
        cotMock = Mockito.mock(Cot.class);
        lnMock = Mockito.mock(Ln.class);
        logMock = Mockito.mock(Log.class);
        try {
            sinIn = new FileReader("src/main/resources/csv/in/SinIn.csv");
            cosIn = new FileReader("src/main/resources/csv/in/CosIn.csv");
            secIn = new FileReader("src/main/resources/csv/in/SecIn.csv");
            tanIn = new FileReader("src/main/resources/csv/in/TanIn.csv");
            cotIn = new FileReader("src/main/resources/csv/in/CotIn.csv");
            lnIn = new FileReader("src/main/resources/csv/in/LnIn.csv");
            log2In = new FileReader("src/main/resources/csv/in/Log2In.csv");
            log5In = new FileReader("src/main/resources/csv/in/Log5In.csv");
            log10In = new FileReader("src/main/resources/csv/in/Log10In.csv");

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(sinIn);
            for (CSVRecord record : records) {
                Mockito.when(sinMock.sin(Double.parseDouble(record.get(0)), eps)).thenReturn(Double.parseDouble(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(cosIn);
            for (CSVRecord record : records) {
                Mockito.when(cosMock.cos(Double.parseDouble(record.get(0)), eps)).thenReturn(Double.parseDouble(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(secIn);
            for (CSVRecord record : records) {
                Mockito.when(secMock.sec(Double.parseDouble(record.get(0)), eps)).thenReturn(Double.parseDouble(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(tanIn);
            for (CSVRecord record : records) {
                Mockito.when(tanMock.tan(Double.parseDouble(record.get(0)), eps)).thenReturn(Double.parseDouble(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(cotIn);
            for (CSVRecord record : records) {
                Mockito.when(cotMock.cot(Double.parseDouble(record.get(0)), eps)).thenReturn(Double.parseDouble(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(lnIn);
            for (CSVRecord record : records) {
                Mockito.when(lnMock.ln(Double.parseDouble(record.get(0)), eps)).thenReturn(Double.parseDouble(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(log2In);
            for (CSVRecord record : records) {
                Mockito.when(logMock.log(2, Double.parseDouble(record.get(0)), eps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(log5In);
            for (CSVRecord record : records) {
                Mockito.when(logMock.log(5, Double.parseDouble(record.get(0)), eps)).thenReturn(Double.valueOf(record.get(1)));
            }
            records = CSVFormat.DEFAULT.parse(log10In);
            for (CSVRecord record : records) {
                Mockito.when(logMock.log(10, Double.parseDouble(record.get(0)), eps)).thenReturn(Double.valueOf(record.get(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csv/in/ExpressionIn.csv")
    public void testViaMocks(double value, double expected) {
        Expression expression = new Expression(secMock, tanMock, cotMock, cosMock, logMock);
        assertEquals(expected, expression.solve(value, funcEps), DELTA);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csv/in/ExpressionIn.csv")
    public void testViaSec(double value, double expected) {
        Expression expression = new Expression(new Sec(cosMock), tanMock, cotMock, cosMock, logMock);
        assertEquals(expected, expression.solve(value, funcEps), DELTA);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csv/in/ExpressionIn.csv")
    public void testViaSecWithCos(double value, double expected) {
        Expression expression = new Expression(new Sec(new Cos(sinMock)), tanMock, cotMock, cosMock, logMock);
        assertEquals(expected, expression.solve(value, funcEps), DELTA);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/csv/in/ExpressionIn.csv")
    public void testViaWithoutMocks(double value, double expected) {
        Expression expression = new Expression();
        assertEquals(expected, expression.solve(value, funcEps), DELTA);
    }
}
