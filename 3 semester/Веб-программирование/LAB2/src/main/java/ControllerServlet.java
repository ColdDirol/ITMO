import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {


            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = request.getReader();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }


            JSONObject json = (JSONObject) new JSONParser().parse(stringBuilder.toString());

            Double x = json.get("x") != null ? Double.parseDouble(json.get("x").toString()) : null;
            Double y = json.get("y") != null ? Double.parseDouble(json.get("y").toString()) : null;
            Double R = json.get("R") != null ? Double.parseDouble(json.get("R").toString()) : null;

            if (x == null || y == null || R == null) {
                throw new NullPointerException("Missing data in JSON.");
            }

            request.setAttribute("x", x);
            request.setAttribute("y", y);
            request.setAttribute("R", R);

            request.getRequestDispatcher("./check").forward(request, response);


        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to parse JSON.");
        } catch (NullPointerException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format in JSON.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}