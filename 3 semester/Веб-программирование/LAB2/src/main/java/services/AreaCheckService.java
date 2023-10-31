package services;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AreaCheckService {
    private static final List<Double> xValues = new ArrayList<>(Arrays.asList(-5.0, -4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0));

    private static final double yMin = -3;
    private static final double yMax = 3;

    private static final double rMin = 1;
    private static final double rMax = 4;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");

    public static String compiledDate(LocalDateTime ldt) {
        return ldt.format(formatter);
    }


    public static boolean check(double x, double y, double R) {
        if(checkData(x, y, R) &&
                (
                        checkIsInRectangle(x, y, R) || checkIsInCircle(x, y, R) || checkIsInTriangle(x, y, R)
                )
        ) {
            return true;
        } else {
            return false;
        }
    }



    private static boolean checkData(double x, double y, double R) {
        return xValues.contains(x)
                && (y >= yMin && y <= yMax)
                && (R >= rMin && R <= rMax);
    }

    // done
    private static boolean checkIsInRectangle(double x, double y, double R) {
        return (y <= 0 && y >= -R && x >= 0 && x <= R);
    }

    // done
    private static boolean checkIsInCircle(double x, double y, double R) {
        if (x <= 0 && y >= 0) {
            double lengthFromNull = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            return lengthFromNull <= R;
        } else {
            return false;
        }
    }

    // done
    private static boolean checkIsInTriangle(double x, double y, double R) {
        double x1 = -R;
        double x2 = 0;
        double x3 = 0;
        double y1 = 0;
        double y2 = 0;
        double y3 = -R / 2;

        double d1 = (x - x2) * (y3 - y2) - (x3 - x2) * (y - y2);
        double d2 = (x - x3) * (y1 - y3) - (x1 - x3) * (y - y3);
        double d3 = (x - x1) * (y2 - y1) - (x2 - x1) * (y - y1);

        boolean hasNegatives = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPositives = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNegatives && hasPositives);
    }
}
