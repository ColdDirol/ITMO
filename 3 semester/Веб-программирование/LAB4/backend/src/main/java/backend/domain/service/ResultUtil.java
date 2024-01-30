package backend.domain.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultUtil {
    private static final double xMin = -5;
    private static final double xMax = 3;

    private static final double yMin = -5;
    private static final double yMax = 3;

    private static final double rMin = -5;
    private static final double rMax = 3;

    public static boolean check(double x, double y, double r) {
        if(checkData(x, y, r)) {
            if(r <= 0) r = r * (-1);
            if(checkIsInRectangle(x, y, r) || checkIsInCircle(x, y, r) || checkIsInTriangle(x, y, r)) {
                return true;
            }
        }

        return false;
    }



    private static boolean checkData(double x, double y, double r) {
        return (x >= xMin && x <= xMax)
                && (y >= yMin && y <= yMax)
                && (r >= rMin && r <= rMax);
    }

    private static boolean checkIsInRectangle(double x, double y, double r) {
        return (y >= 0 && y <= r/2 && x >= 0 && x <= r);
    }

    private static boolean checkIsInCircle(double x, double y, double r) {
        if (x <= 0 && y >= 0) {
            double lengthFromNull = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            return lengthFromNull <= r;
        } else
            return false;
    }

    // done
    private static boolean checkIsInTriangle(double x, double y, double r) {
        double x1 = r / 2;
        double x2 = 0;
        double x3 = 0;
        double y1 = 0;
        double y2 = 0;
        double y3 = -r;

        double d1 = (x - x2) * (y3 - y2) - (x3 - x2) * (y - y2);
        double d2 = (x - x3) * (y1 - y3) - (x1 - x3) * (y - y3);
        double d3 = (x - x1) * (y2 - y1) - (x2 - x1) * (y - y1);

        boolean hasNegatives = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPositives = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNegatives && hasPositives);
    }
}
