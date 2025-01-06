package com.coursework.user_management_service.domain.service.user.action

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import user.external.RegisterRequestDto
import user.external.TokenDto

@Service
class RegisterUserAction {
    operator fun invoke(request: RegisterRequestDto): TokenDto {
        return TokenDto("token")
    }
}