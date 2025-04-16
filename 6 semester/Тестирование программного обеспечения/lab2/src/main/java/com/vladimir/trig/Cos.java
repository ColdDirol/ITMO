package com.vladimir.trig;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Cos {

    private final Sin sin;

    public Cos(Sin sin) {
        this.sin = sin;
    }

    public Cos() {
        this.sin = new Sin();
    }

    public double cos(double x, double eps) {
        double x_init = x;
        x %= Math.PI * 2;
        if (Double.POSITIVE_INFINITY == x || Double.NEGATIVE_INFINITY == x) {
            return Double.NaN;
        }
        if (x < -Math.PI) {
            while (x < -Math.PI) x += 2 * Math.PI;
        }
        if (x > Math.PI) {
            while (x > Math.PI) x -= 2 * Math.PI;
        }
        double result;
        if (x > Math.PI / 2 || x < -Math.PI / 2) {
            result = -1 * Math.sqrt(1 - sin.sin(x_init, eps) * sin.sin(x_init, eps));
        } else result = Math.sqrt(1 - sin.sin(x_init, eps) * sin.sin(x_init, eps));
        if (Math.abs(result) > 1) return Double.NaN;
        if (Math.abs(result) <= eps) return 0;
        return result;
    }

    public double writeCSV(double x, double eps, Writer out) {
        double res = cos(x, eps);
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
