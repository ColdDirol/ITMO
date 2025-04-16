package com.vladimir.trig;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Sin {

    public double sin(double x, double eps) {
        if (Double.POSITIVE_INFINITY == x || Double.NEGATIVE_INFINITY == x) {
            return Double.NaN;
        }
        double fact = 1, result_l = 1, result = 0, xx, pow;
        int sign = 1, cnt = 1;

        if (x >= 0) {
            while (x > Math.PI * 2) {
                x -= Math.PI * 2;
            }
        } else if (x < 0) {
            while (x < Math.PI * 2) {
                x += Math.PI * 2;
            }
        }
        xx = x * x;
        pow = x;

        while (Math.abs(result_l - result) > eps) {
            fact /= cnt;
            result_l = result;
            result += sign * pow * fact;
            sign = -sign;
            fact /= (cnt + 1);
            pow *= xx;
            cnt += 2;
        }
        if (Math.abs(result) > 1) return Double.NaN;
        if (Math.abs(result) < eps) return 0;
        return result;
    }

    public double writeCSV(double x, double eps, Writer out) {
        double res = sin(x, eps);
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
