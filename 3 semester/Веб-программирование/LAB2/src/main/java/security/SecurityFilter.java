package security;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import services.PathService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // init
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Получаем путь запроса
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();

//        String token = httpRequest.getHeader("Authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7); // Удалить "Bearer " из заголовка
//
//            try {
//                DecodedJWT jwt = JWTUtil.verifyToken(token);
//
//                if ("admin".equals(jwt.getClaim("username").asString())) {
//                    chain.doFilter(request, response);
//                } else {
//                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                }
//            } catch (JWTVerificationException e) {
//                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            }
//        } else {
//            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }

//        if (path.contains("/session") || path.contains("/controller") ||  path.contains("/check")) {
        if (path.contains("/controller") ||  path.contains("/check")) {
            String token = httpRequest.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // Удалить "Bearer " из заголовка

                try {
                    DecodedJWT jwt = JWTUtil.verifyToken(token);

                    if ("admin".equals(jwt.getClaim("username").asString())) {
                        chain.doFilter(request, response);
                    } else {
                        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    }
                } catch (JWTVerificationException e) {
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

            chain.doFilter(request, response);
        } else {
            // do nothing
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // destroy
    }
}
