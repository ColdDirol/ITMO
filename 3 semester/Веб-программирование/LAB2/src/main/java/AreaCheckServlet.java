import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {
    private final List<Double> xValues = new ArrayList<>(Arrays.asList(-5.0, -4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0));

    private final double yMin = -3;
    private final double yMax = 3;

    private final double rMin = 1;
    private final double rMax = 4;

    private double lengthFromNull;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }


    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            Double x = request.getAttribute("x") != null ? Double.parseDouble(request.getAttribute("x").toString()) : null;
            Double y = request.getAttribute("y") != null ? Double.parseDouble(request.getAttribute("y").toString()) : null;
            Double R = request.getAttribute("R") != null ? Double.parseDouble(request.getAttribute("R").toString()) : null;

            boolean result = check(x, y, R);
            LocalDateTime compiledIn = LocalDateTime.now();

            JSONObject responseJson = new JSONObject();
            responseJson.put("x", x);
            responseJson.put("y", y);
            responseJson.put("R", R);
            responseJson.put("result", result);
            responseJson.put("compiled_in", compiledDate(compiledIn));

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(responseJson.toJSONString());

        } catch (NullPointerException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing data in JSON.");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format in JSON.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    private String compiledDate(LocalDateTime ldt) {
        return ldt.format(formatter);
    }


    public boolean check(double x, double y, double R) {
        if(checkData(x, y, R) &&
                (
                    checkIsInRectangle(x, y, R)
                    || checkIsInCircle(x, y, R)
                    || checkIsInTriangle(x, y, R)
                )
        ) {
            return true;
        } else {
            return false;
        }
    }



    private boolean checkData(double x, double y, double R) {
        return xValues.contains(x)
                && (y >= yMin && y <= yMax)
                && (R >= rMin && R <= rMax);
    }

    // done
    private boolean checkIsInRectangle(double x, double y, double R) {
        return (y <= 0 && y >= -R && x >= 0 && x <= R);
    }

    // done
    private boolean checkIsInCircle(double x, double y, double R) {
        if (x <= 0 && y >= 0) {
            lengthFromNull = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            return lengthFromNull <= R;
        } else {
            return false;
        }
    }

    // done
    public static boolean checkIsInTriangle(double x, double y, double R) {
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