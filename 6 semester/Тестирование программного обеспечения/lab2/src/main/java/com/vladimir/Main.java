package com.vladimir;

import com.vladimir.log.Ln;
import com.vladimir.log.Log;
import com.vladimir.trig.*;
import com.vladimir.utils.ChartBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        String base = "src/main/resources/csv/out/";
        String out = "src/main/resources/charts/";

        Double eps = 0.1;

        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Sec sec = new Sec(cos);
        Cot cot = new Cot(sin, cos);
        Ln ln = new Ln();
        Log log = new Log(ln);

        createRandomValues(base);

        ChartBuilder.drawChartFromCSVAndFunction(
                "Sin", x -> sin.sin(x, eps), -100, 100, 0.1, base + "SinOut.csv", out + "SinChart.png");
        ChartBuilder.drawChartFromCSVAndFunction(
                "Cos", x -> cos.cos(x, eps), -100, 100, 0.1, base + "CosOut.csv", out + "CosChart.png");
        ChartBuilder.drawChartFromCSVAndFunction(
                "Tan", x -> tan.tan(x, eps), -100, 100, 0.1, base + "TanOut.csv", out + "TanChart.png");
        ChartBuilder.drawChartFromCSVAndFunction(
                "Sec", x -> sec.sec(x, eps), -100, 100, 0.1, base + "SecOut.csv", out + "SecChart.png");
        ChartBuilder.drawChartFromCSVAndFunction(
                "Cot", x -> cot.cot(x, eps), -100, 100, 0.1, base + "CotOut.csv", out + "CotChart.png");
        ChartBuilder.drawChartFromCSVAndFunction(
                "Ln", x -> ln.ln(x, eps), -10, 100, 0.1, base + "LnOut.csv", out + "LnChart.png");
        ChartBuilder.drawChartFromCSVAndFunction(
                "Log2", x -> log.log(2, x, eps), -10, 100, 0.1, base + "Log2Out.csv", out + "Log2Chart.png");
        ChartBuilder.drawChartFromCSVAndFunction(
                "Log5", x -> log.log(5, x, eps), -10, 100, 0.1, base + "Log5Out.csv", out + "Log5Chart.png");
        ChartBuilder.drawChartFromCSVAndFunction(
                "Log10", x -> log.log(10, x, eps), -100, 100, 0.1, base + "Log10Out.csv", out + "Log10Chart.png");
        ChartBuilder.drawChartFromCSVAndFunction(
                "Expression", x -> new Expression().solve(x, 0.1), -10, 100, 0.1, base + "ExpressionOut.csv", out + "ExpressionChart.png");
    }

    public static void createRandomValues(String path) {
        Double eps = 0.1;

        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Sec sec = new Sec(cos);
        Cot cot = new Cot(sin, cos);
        Ln ln = new Ln();
        Log log = new Log(ln);

        try (Writer writerSin = new FileWriter(path + "SinOut.csv");
             Writer writerCos = new FileWriter(path + "CosOut.csv");
             Writer writerTan = new FileWriter(path + "TanOut.csv");
             Writer writerSec = new FileWriter(path + "SecOut.csv");
             Writer writerCot = new FileWriter(path + "CotOut.csv");
             Writer writerLog2 = new FileWriter(path + "Log2Out.csv");
             Writer writerLog5 = new FileWriter(path + "Log5Out.csv");
             Writer writerLog10 = new FileWriter(path + "Log10Out.csv");
             Writer writerLn = new FileWriter(path + "LnOut.csv");
             Writer writerExpression = new FileWriter(path + "ExpressionOut.csv")) {

            try (CSVPrinter printerSin = CSVFormat.DEFAULT.print(writerSin);
                 CSVPrinter printerCos = CSVFormat.DEFAULT.print(writerCos);
                 CSVPrinter printerTan = CSVFormat.DEFAULT.print(writerTan);
                 CSVPrinter printerSec = CSVFormat.DEFAULT.print(writerSec);
                 CSVPrinter printerCot = CSVFormat.DEFAULT.print(writerCot);
                 CSVPrinter printerLog2 = CSVFormat.DEFAULT.print(writerLog2);
                 CSVPrinter printerLog5 = CSVFormat.DEFAULT.print(writerLog5);
                 CSVPrinter printerLog10 = CSVFormat.DEFAULT.print(writerLog10);
                 CSVPrinter printerLn = CSVFormat.DEFAULT.print(writerLn);
                 CSVPrinter printerExpression = CSVFormat.DEFAULT.print(writerExpression)) {

                List<Double> testValues = generateRandomValues(100, -100d, 100d);

                for (Double x : testValues) {
                    System.out.println(x);
                    printerSin.printRecord(x, sin.sin(x, eps));
                    printerCos.printRecord(x, cos.cos(x, eps));
                    printerTan.printRecord(x, tan.tan(x, eps));
                    printerSec.printRecord(x, sec.sec(x, eps));
                    printerCot.printRecord(x, cot.cot(x, eps));
                    printerLn.printRecord(x, ln.ln(x, eps));
                    printerLog2.printRecord(x, log.log(2, x, eps));
                    printerLog5.printRecord(x, log.log(5, x, eps));
                    printerLog10.printRecord(x, log.log(10, x, eps));

                    printerExpression.printRecord(x, new Expression().solve(x, eps));
                }

                System.out.println("CSV files have been generated in 'src/main/resources/csv/out'.");

            } catch (IOException e) {
                System.out.println("Error writing to CSV files: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error opening files: " + e.getMessage());
        }
    }

    public static List<Double> generateRandomValues(int count, double min, double max) {
        Random rand = new Random();
        return IntStream.range(0, count)
                .mapToDouble(i -> min + (max - min) * rand.nextDouble())
                .boxed()
                .collect(Collectors.toList());
    }

    public static void createTestValues() {
        Double eps = 0.1;

        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Sec sec = new Sec(cos);
        Cot cot = new Cot(sin, cos);
        Log log = new Log();
        Ln ln = new Ln();

        try (Writer writerSin = new FileWriter("src/main/resources/csv/out/SinOut.csv");
             Writer writerCos = new FileWriter("src/main/resources/csv/out/CosOut.csv");
             Writer writerTan = new FileWriter("src/main/resources/csv/out/TanOut.csv");
             Writer writerSec = new FileWriter("src/main/resources/csv/out/SecOut.csv");
             Writer writerCot = new FileWriter("src/main/resources/csv/out/CotOut.csv");
             Writer writerLog2 = new FileWriter("src/main/resources/csv/out/Log2Out.csv");
             Writer writerLog5 = new FileWriter("src/main/resources/csv/out/Log5Out.csv");
             Writer writerLog10 = new FileWriter("src/main/resources/csv/out/Log10Out.csv");
             Writer writerLn = new FileWriter("src/main/resources/csv/out/LnOut.csv");
             Writer writerExpression = new FileWriter("src/main/resources/csv/out/ExpressionOut.csv")) {

            try (CSVPrinter printerSin = CSVFormat.DEFAULT.print(writerSin);
                 CSVPrinter printerCos = CSVFormat.DEFAULT.print(writerCos);
                 CSVPrinter printerTan = CSVFormat.DEFAULT.print(writerTan);
                 CSVPrinter printerSec = CSVFormat.DEFAULT.print(writerSec);
                 CSVPrinter printerCot = CSVFormat.DEFAULT.print(writerCot);
                 CSVPrinter printerLog2 = CSVFormat.DEFAULT.print(writerLog2);
                 CSVPrinter printerLog5 = CSVFormat.DEFAULT.print(writerLog5);
                 CSVPrinter printerLog10 = CSVFormat.DEFAULT.print(writerLog10);
                 CSVPrinter printerLn = CSVFormat.DEFAULT.print(writerLn);
                 CSVPrinter printerExpression = CSVFormat.DEFAULT.print(writerExpression);) {

                Double[] testValues = {-1.0, -0.5, 0.0, 0.5, 1.0, -0.121212, 0.121212, -100.5437, 100.5437, -2.3918483494, 2.3918483494, Double.parseDouble("Infinity")};

                for (Double x : testValues) {
                    printerSin.printRecord(x, sin.sin(x, eps));
                    printerCos.printRecord(x, cos.cos(x, eps));
                    printerTan.printRecord(x, tan.tan(x, eps));
                    printerSec.printRecord(x, sec.sec(x, eps));
                    printerCot.printRecord(x, cot.cot(x, eps));

                    printerLog2.printRecord(x, log.log(2d, x, eps));
                    printerLog5.printRecord(x, log.log(5d, x, eps));
                    printerLog10.printRecord(x, log.log(10d, x, eps));
                    printerLn.printRecord(x, ln.ln(x, eps));

                    printerExpression.printRecord(x, new Expression().solve(x, eps));
                }

                System.out.println("CSV files have been generated in 'src/main/resources/csv/out'.");

            } catch (IOException e) {
                System.out.println("Error writing to CSV files: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error opening files: " + e.getMessage());
        }
    }
}
