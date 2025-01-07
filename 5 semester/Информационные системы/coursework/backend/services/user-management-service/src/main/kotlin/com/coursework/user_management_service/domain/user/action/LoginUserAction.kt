package com.coursework.user_management_service.domain.user.action

import com.coursework.user_management_service.security.JwtUserDetailsService
import com.coursework.user_management_service.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import user.external.AuthenticationRequestDto
import user.external.TokenDto

@Service
class LoginUserAction(
    private val authManager: AuthenticationManager,
    private val userDetailsService: JwtUserDetailsService,
    private val jwtService: JwtService,
    private val encoder: PasswordEncoder
) {
    operator fun invoke(request: AuthenticationRequestDto): TokenDto {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        val user = userDetailsService.loadUserByUsername(request.email)

        if (user.password != encoder.encode(request.password)) {
            throw IllegalArgumentException("Invalid password to account with email: ${request.email}")
        }

        return TokenDto(
            jwtService.createAccessToken(user)
        )
    }

}