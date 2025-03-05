package vladimir;

public class MathUtils {

    private static final int MAX_ITERATIONS = 1000;
    private static final double EPSILON = 1e-10;

    public static double arccos(double x) {
        if (x < -1 || x > 1) {
            throw new IllegalArgumentException("x must be in the range [-1, 1]");
        }

        double result = Math.PI / 2;
        double term = x;
        double n = 0;

        while (Math.abs(term) > EPSILON && n < MAX_ITERATIONS) {
            result -= term;
            n++;
            term = (term * x * x * (2 * n - 1) * (2 * n - 1)) / (2 * n * (2 * n + 1));
        }

        return result;
    }
}