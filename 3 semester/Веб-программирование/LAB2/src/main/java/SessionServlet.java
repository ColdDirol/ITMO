import beans.ResponseBean;
import services.SessionService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<ResponseBean> results = SessionService.getResultBeans(request.getSession());
            JSONArray resultsJsonArray = new JSONArray();

            for (ResponseBean result : results) {
                JSONObject resultJson = new JSONObject();
                resultJson.put("x", result.getX());
                resultJson.put("y", result.getY());
                resultJson.put("R", result.getR());
                resultJson.put("result", result.isResult());
                resultJson.put("compiled_in", result.getCompiledIn());
                resultsJsonArray.add(resultJson);
            }

            PrintWriter out = response.getWriter();
            out.print(resultsJsonArray.toJSONString());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        SessionService.clearBeans(request.getSession());
    }
}