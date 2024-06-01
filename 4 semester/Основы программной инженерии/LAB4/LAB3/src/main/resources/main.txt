import beans.AreaCheckService;

public class Main {
    public static void main(String[] args) {
        double x = 0;
        double y = 0;
        double r = 2;
        boolean result = AreaCheckService.check(x, y, r);
        System.out.println("Result is " + result);
    }
}
