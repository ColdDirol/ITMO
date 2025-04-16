package com.vladimir.utils;

import com.vladimir.log.Ln;
import com.vladimir.log.Log;
import com.vladimir.trig.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.DoubleFunction;

public class ChartBuilder {

    public static void drawChartFromCSVAndFunction(
            String title,
            DoubleFunction<Double> function,
            double start,
            double end,
            double step,
            String csvPath,
            String outputPath
    ) {
        XYSeries functionSeries = new XYSeries(title + " Function");
        XYSeries pointSeries = new XYSeries(title + " CSV Points");

        for (double x = start; x <= end; x += step) {
            double y = function.apply(x);
            if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                functionSeries.add(x, y);
            }
        }

        try (CSVParser parser = CSVFormat.DEFAULT.parse(new FileReader(csvPath))) {
            for (CSVRecord record : parser) {
                try {
                    double x = Double.parseDouble(record.get(0));
                    double y = Double.parseDouble(record.get(1));
                    if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                        pointSeries.add(x, y);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid CSV value: " + record);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
            return;
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(functionSeries);
        dataset.addSeries(pointSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);

        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);

        plot.setRenderer(renderer);

        try {
            ChartUtils.saveChartAsPNG(new File(outputPath), chart, 800, 600);
        } catch (IOException e) {
            System.err.println("Failed to save chart: " + e.getMessage());
        }
    }

}
