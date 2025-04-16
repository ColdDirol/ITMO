package com.vladimir.log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Log  {

    private final Ln ln;

    public Log(Ln ln) {
        this.ln = ln;
    }

    public Log() {
        this.ln = new Ln();
    }

    public double log(double a, double b, double esp) {
        return ln.ln(b, esp) / ln.ln(a, esp);
    }

    public double writeCSV(double a, double x, double eps, Writer out) {
        double res = log(a, x, eps);
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
