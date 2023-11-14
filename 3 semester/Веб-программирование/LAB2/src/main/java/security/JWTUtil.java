package security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {
    private static final String SECRET = "secret"; // Замените на ваш секретный ключ

    public static String createTokenForAdmin() {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withClaim("username", "admin")
                .withClaim("password", "admin")
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.require(algorithm).build().verify(token);
    }
}
