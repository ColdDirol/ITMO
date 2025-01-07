package com.coursework.user_management_service.domain.user.action

import com.coursework.user_management_service.security.JwtService
import com.coursework.user_management_service.infrastructure.model.UsersEntity
import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import internal.RoleType
import internal.UserStatusType
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import user.external.RegisterRequestDto
import user.external.TokenDto

@Service
class RegisterUserAction(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder
) {
    operator fun invoke(request: RegisterRequestDto): TokenDto {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already in use")
        }

        val user = UsersEntity(
            id = 0,
            email = request.email,
            password = encoder.encode(request.password),
            role = RoleType.USER,
            phone = request.phone,
            name = request.fullName,
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
                    .build()
            )
        )
    }
}