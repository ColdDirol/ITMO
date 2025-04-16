package unit;

import com.vladimir.utils.ChartBuilder;
import org.jfree.chart.JFreeChart;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.DoubleFunction;

import static org.junit.jupiter.api.Assertions.*;

public class ChartBuilderTest {

    @Test
    void testDrawChartFromCSVAndFunction() throws IOException {
        DoubleFunction<Double> function = x -> x * x; // просто квадрат
        File csvFile = File.createTempFile("testData", ".csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("1,1\n");
            writer.write("2,4\n");
            writer.write("3,9\n");
        }

        String outputPath = "testChart.png";

        ChartBuilder.drawChartFromCSVAndFunction("Test Chart", function, 0, 3, 1, csvFile.getAbsolutePath(), outputPath);

        File outputFile = new File(outputPath);
        assertTrue(outputFile.exists(), "Chart image should be created.");
    }

    @Test
    void testInvalidCSV() throws IOException {
        File csvFile = File.createTempFile("invalidData", ".csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("1,1\n");
            writer.write("invalid,4\n");
            writer.write("3,notANumber\n");
        }

        DoubleFunction<Double> function = x -> x * x;

        String outputPath = "testChartInvalid.png";

        ChartBuilder.drawChartFromCSVAndFunction("Invalid CSV Test", function, 0, 3, 1, csvFile.getAbsolutePath(), outputPath);

        File outputFile = new File(outputPath);
        assertTrue(outputFile.exists(), "Chart image should be created even with invalid CSV.");
    }
}