package com.vladimir;

import com.vladimir.log.Log;
import com.vladimir.other.Pow;
import com.vladimir.trig.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Expression {

    private Sec sec;
    private Tan tan;
    private Cot cot;
    private Cos cos;

    private Log log;

    public Expression() {
        Sin sin = new Sin();
        this.cos = new Cos(sin);

        this.sec = new Sec(this.cos);
        this.tan = new Tan(sin, this.cos);
        this.cot = new Cot(sin, this.cos);

        this.log = new Log();
    }

    public Expression(Sec sec, Tan tan, Cot cot, Cos cos, Log log) {
        this.sec = sec;
        this.tan = tan;
        this.cot = cot;
        this.cos = cos;
        this.log = log;
    }

    public double solve(double x, double eps) {
        if (x <= 0) {
            return ((Pow.pow(Pow.pow((sec.sec(x, eps) + tan.tan(x, eps)), 3), 2) / cot.cot(x, eps))
                    / (sec.sec(x, eps) * cos.cos(x, eps)));
        } else {
            return (((Pow.pow(Pow.pow(log.log(10, x, eps), 2), 2) + ((log.log(10, x, eps) - log.log(5, x, eps)) * log.log(2, x, eps)))
                    / log.log(5, x, eps)) * ((log.log(2, x, eps) + log.log(5, x, eps)) + log.log(10, x, eps)));
        }
    }

    public double writeCSV(double x, double eps, Writer out) {
        double res = solve(x, eps);
        try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
            printer.printRecord(x, res);
        } catch (IOException e) {
            System.out.println(
                    e.getMessage()
            );
        }
        return res;
    }

//    x <= 0 : (((((sec(x) + tan(x)) ^ 3) ^ 2) / cot(x)) / (sec(x) * cos(x)))
//    x > 0 : (((((log_10(x) ^ 2) ^ 2) + ((log_10(x) - log_5(x)) * log_2(x))) / log_5(x)) * ((log_2(x) + log_5(x)) + log_10(x)))
}