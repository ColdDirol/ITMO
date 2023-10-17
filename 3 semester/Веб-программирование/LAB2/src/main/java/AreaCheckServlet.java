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


//    $data = [
//            'x' => $x,
//            'y' => $y,
//            'r' => $R,
//            'result' => ($isInRectangle || $isInCircle || $isInTriangle) ? 'true' : 'false',
//            'compiled_in' => $compilationTime,
//            ];

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }


    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(stringBuilder.toString());
            double x = (double) json.get("x");
            double y = (double) json.get("y");
            double R = (double) json.get("R");

            boolean result = check(x, y, R);

            JSONObject responseJson = new JSONObject();
            responseJson.put("x", x);
            responseJson.put("y", y);
            responseJson.put("R", R);
            responseJson.put("result", result);
            responseJson.put("compiled_in", LocalDateTime.now().toString());

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(responseJson.toJSONString());

        } catch (Exception e) {
            // Обработка ошибок парсинга JSON или других исключений
            e.printStackTrace();
        }
    }


    public boolean check(double x, double y, double R) {
        if(checkData(x, y, R) && checkIsInCircle(x, y, R) && checkIsInTriangle(x, y, R)) {
            return true;
        } else {
            return false;
        }
    }



    private boolean checkData(double x, double y, double R) {
        return xValues.contains(x)
                && (yMin <= y && y <= yMax)
                && (rMin <= R && R <= rMax);
    }

    private boolean checkIsInCircle(double x, double y, double R) {
        if (x >= 0 && y <= 0) {
            lengthFromNull = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            return lengthFromNull <= R / 2;
        } else {
            return false;
        }
    }

    public static boolean checkIsInTriangle(double x, double y, double R) {
        double x1 = -R / 2;
        double x2 = 0;
        double x3 = 0;
        double y1 = 0;
        double y2 = 0;
        double y3 = -R;

        double d1 = (x - x2) * (y3 - y2) - (x3 - x2) * (y - y2);
        double d2 = (x - x3) * (y1 - y3) - (x1 - x3) * (y - y3);
        double d3 = (x - x1) * (y2 - y1) - (x2 - x1) * (y - y1);

        boolean hasNegatives = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPositives = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNegatives && hasPositives);
    }
}