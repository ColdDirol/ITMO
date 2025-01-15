package com.coursework.user_management_service.domain.auth.action

import com.coursework.user_management_service.security.JwtService
import com.coursework.user_management_service.infrastructure.model.UsersEntity
import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import internal.RoleType
import internal.UserStatusType
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import user.external.RegisterRequestDto
import user.external.TokenDto

@Service
open class RegisterUserAction(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder
) {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    open operator fun invoke(request: RegisterRequestDto): TokenDto {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already in use")
        }

        val user = UsersEntity(
            id = 0,
            email = request.email,
            password = encoder.encode(request.password),
            role = RoleType.USER,
            phone = request.phone,
            name = request.name,
            sex = request.sex,
            dateOfBirth = request.dateOfBirth,
            status = UserStatusType.ACTIVE
        )

        userRepository.save(user)

        return TokenDto(
            jwtService.createAccessToken(
                User.builder()
                    .username(user.email)
                    .password(user.password)
                    .roles(user.role.name)
                    .build(),
                mapOf(
                    "name" to (user.name),
                    "email" to (user.email),
                    "phone" to (user.phone),
                    "role" to (user.role),
                    "sex" to (user.sex),
                    "pageElementsLimit" to (10),
                    "iat" to System.currentTimeMillis() / 1000,
                    "exp" to (System.currentTimeMillis() + jwtService.getAccessTokenExpiration().time) / 1000
                )
            )
        )
    }
}