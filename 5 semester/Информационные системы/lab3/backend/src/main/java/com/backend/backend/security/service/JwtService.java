package com.backend.backend.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.backend.backend.security.model.entity.Users;

import java.util.Date;

public class JwtService {

    private static final String SECRET = "secret";
    private static final long EXPIRATION_TIME = 86400000; // 1 день в миллисекундах

    public static String createToken(Users user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().name())
                .withClaim("pageElementsLimit", 15)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) throws JWTDecodeException {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            throw new JWTDecodeException("Invalid token");
        }
        return decodedJWT;
    }

    public static String extractEmail(String token) {
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = verifyToken(token);
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Invalid token");
        }
        return decodedJWT.getClaim("email").asString();
    }

    public static String extractRole(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getClaim("role").asString();
    }

//    public static boolean isTokenExpired(String token) {
//        DecodedJWT decodedJWT = null;
//        try {
//            decodedJWT = verifyToken(token);
//        } catch (JWTVerificationException e) {
//            throw new JWTDecodeException("Invalid token");
//        }
//        Date expirationDate = decodedJWT.getExpiresAt();
//        return expirationDate == null || expirationDate.before(new Date());
//    }

    public static boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            Date expirationDate = decodedJWT.getExpiresAt();
            return expirationDate == null || expirationDate.before(new Date());
        } catch (JWTVerificationException e) {
            return true;
        }
    }
}
