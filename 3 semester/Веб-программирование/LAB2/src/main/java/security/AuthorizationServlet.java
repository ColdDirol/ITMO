package security;

import beans.LoginBean;
import beans.TokenBean;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotAuthorizedException;
import java.io.IOException;

@WebServlet("/login")
public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LoginBean loginBean = new LoginBean(request);
            TokenBean tokenBean = null;
            
            if("admin".equals(loginBean.getUsername()) && "admin".equals(loginBean.getPassword())) {
                tokenBean = new TokenBean(JWTUtil.createTokenForAdmin());
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(tokenBean.toJson().toJSONString());
            
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}