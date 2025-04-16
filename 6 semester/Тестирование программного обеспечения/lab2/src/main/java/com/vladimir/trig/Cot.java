package com.vladimir.trig;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Cot {

    private final Sin sin;
    private final Cos cos;

    public Cot(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    public Cot() {
        this.sin = new Sin();
        this.cos = new Cos(sin);
    }

    public double cot(double x, double eps) {
        double sinVal = sin.sin(x, eps);
        double cosVal = cos.cos(x, eps);
        if (Double.isNaN(sinVal) || Double.isNaN(cosVal) || sinVal == 0) return Double.NaN;
        return cosVal / sinVal;
    }

    public double writeCSV(double x, double eps, Writer out) {
        double res = cot(x, eps);
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