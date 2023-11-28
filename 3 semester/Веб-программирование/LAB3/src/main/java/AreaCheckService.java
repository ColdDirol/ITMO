import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AreaCheckService {
    private static final List<Double> xValues = new ArrayList<>(Arrays.asList(-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0));

    private static final double xMin = -3;
    private static final double xMax = 5;

    private static final double yMin = -3;
    private static final double yMax = 5;

    private static final double rMin = -3;
    private static final double rMax = 5;

    private static final List<Double> rValues = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");

    public static String compiledDate(LocalDateTime ldt) {
        return ldt.format(formatter);
    }


    public static boolean check(double x, double y, double r) {
        if(checkData(x, y, r) &&
                (
                        checkIsInRectangle(x, y, r) || checkIsInCircle(x, y, r) || checkIsInTriangle(x, y, r)
                )
        ) {
            return true;
        } else {
            return false;
        }
    }



    private static boolean checkData(double x, double y, double r) {
        //return xValues.contains(x) && (y >= yMin && y <= yMax) && rValues.contains(r);

        return (x >= xMin && x <= xMax)
                && (y >= yMin && y <= yMax)
                && (r >= rMin && r <= rMax);
    }

    // done
    private static boolean checkIsInRectangle(double x, double y, double r) {
        return (y >= 0 && y <= r && x >= 0 && x <= r);
    }

    // done
    private static boolean checkIsInCircle(double x, double y, double r) {
        if (x <= 0 && y >= 0) {
            double lengthFromNull = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            return lengthFromNull <= r;
        } else {
            return false;
        }
    }

    // done
    private static boolean checkIsInTriangle(double x, double y, double r) {
        double x1 = -r / 2;
        double x2 = 0;
        double x3 = 0;
        double y1 = 0;
        double y2 = 0;
        double y3 = -r / 2;

        double d1 = (x - x2) * (y3 - y2) - (x3 - x2) * (y - y2);
        double d2 = (x - x3) * (y1 - y3) - (x1 - x3) * (y - y3);
        double d3 = (x - x1) * (y2 - y1) - (x2 - x1) * (y - y1);

        boolean hasNegatives = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPositives = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNegatives && hasPositives);
    }
}