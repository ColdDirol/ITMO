import beans.RequestBean;
import beans.ResponseBean;
import services.SessionService;
import services.AreaCheckService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

import static services.AreaCheckService.compiledDate;

/**
 * AreaCheckServlet with "/check" endpoint
 * Allowed methods: POST
 */
@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {
    /**
     * Override method from HttpServlet.
     * Redirects the call to the processRequest() method.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    /**
     * Receives a call from the doPost() method, processes it and sends a response.
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            RequestBean requestBean = (RequestBean) request.getAttribute("request");

            Double x = requestBean.getX();
            Double y = requestBean.getY();
            Double R = requestBean.getR();

            Boolean result = AreaCheckService.check(x, y, R);
            String compiledIn = compiledDate(LocalDateTime.now());


            ResponseBean responseBean = new ResponseBean(x, y, R, result, compiledIn);
            SessionService.addResultBean((HttpSession) request.getAttribute("session"), responseBean);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

//            response.getWriter().write(request.getHeader("Referer"));
            response.getWriter().write(responseBean.toJson().toJSONString());

        } catch (NullPointerException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing data in JSON.");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format in JSON.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}