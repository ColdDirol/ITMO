package com.coursework.user_management_service.security

import org.springframework.stereotype.Service

@Service
class JWTService {

    companion object {
        private const val SECRET_KEY = "55547fd80550d1a47c90616541fb8eae645e58bf445961411a5eef1cd8f94ba1"
    }

    fun extractJwtTokenFromHeader(authHeader: String?): String? {
        return authHeader?.takeIf { it.startsWith("Bearer ") }?.substring(7)
    }

    fun extractUsername(token: String): String {
        return extractClaim(token) { it.subject }
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun generateToken(userDetails: UserDetails): String {
        return generateToken(emptyMap(), userDetails)
    }

    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 24 минуты
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}