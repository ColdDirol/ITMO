import beans.ResultBean;
import beans.ResultBeanManager;
import org.json.simple.JSONObject;
import services.AreaCheckService;
import services.JSONService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

import static services.AreaCheckService.compiledDate;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }


    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Double x = request.getAttribute("x") != null ? Double.parseDouble(request.getAttribute("x").toString()) : null;
            Double y = request.getAttribute("y") != null ? Double.parseDouble(request.getAttribute("y").toString()) : null;
            Double R = request.getAttribute("R") != null ? Double.parseDouble(request.getAttribute("R").toString()) : null;
            Boolean result = AreaCheckService.check(x, y, R);
            String compiledIn = compiledDate(LocalDateTime.now());


            ResultBean resultBean = ResultBeanManager.parseToResultBean(x, y, R, result, compiledIn);
            ResultBeanManager.addResultBean((HttpSession) request.getAttribute("session"), resultBean);


            JSONObject responseJson = JSONService.parseToResponceJSON(x, y, R, result, compiledIn);

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
}