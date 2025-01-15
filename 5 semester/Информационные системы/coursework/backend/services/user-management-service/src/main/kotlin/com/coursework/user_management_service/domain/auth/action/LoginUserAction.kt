package com.coursework.user_management_service.domain.auth.action

import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import com.coursework.user_management_service.security.JwtUserDetailsService
import com.coursework.user_management_service.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import user.external.AuthenticationRequestDto
import user.external.TokenDto

@Service
open class LoginUserAction(
    private val authManager: AuthenticationManager,
    private val userDetailsService: JwtUserDetailsService,
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder
) {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    open operator fun invoke(request: AuthenticationRequestDto): TokenDto {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        val user = userDetailsService.loadUserByUsername(request.email)

        println("user.password: " + user.password)
        println("request.password: " + request.password)
        println("encoder.encode(request.password): " + encoder.encode(request.password))

//        if (user.password != encoder.encode(request.password)) {
//            throw IllegalArgumentException("Invalid password to account with email: ${request.email}")
//        }
        if (!encoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Invalid password to account with email: ${request.email}")
        }

        val userFromDb = userRepository.findByEmail(request.email)
            .orElseThrow{ UsernameNotFoundException("User not found") }

        return TokenDto(
            jwtService.createAccessToken(
                user,
                mapOf(
                    "name" to (userFromDb.name),
                    "email" to (userFromDb.email),
                    "phone" to (userFromDb.phone),
                    "role" to (userFromDb.role),
                    "sex" to (userFromDb.sex),
                    "pageElementsLimit" to (10),
                    "iat" to System.currentTimeMillis() / 1000,
                    "exp" to (System.currentTimeMillis() + jwtService.getAccessTokenExpiration().time) / 1000
                )
            )
        )
    }

}