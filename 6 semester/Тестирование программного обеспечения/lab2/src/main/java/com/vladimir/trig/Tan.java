package com.vladimir.trig;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Tan {

    private final Sin sin;
    private final Cos cos;

    public Tan(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    public Tan() {
        this.sin = new Sin();
        this.cos = new Cos(sin);
    }

    public double tan(double x, double eps) {
        double sinVal = sin.sin(x, eps);
        double cosVal = cos.cos(x, eps);
        if (Double.isNaN(sinVal) || Double.isNaN(cosVal) || cosVal == 0) return Double.NaN;
        return sinVal / cosVal;
    }

    public double writeCSV(double x, double eps, Writer out) {
        double res = tan(x, eps);
        try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
            printer.printRecord(x, res);
        } catch (IOException e) {
            System.out.println(
                    e.getMessage()
            );
        }
        return res;
    }
}
