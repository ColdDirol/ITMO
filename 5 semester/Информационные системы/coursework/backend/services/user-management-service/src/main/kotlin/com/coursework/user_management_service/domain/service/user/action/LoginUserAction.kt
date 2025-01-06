package com.coursework.user_management_service.domain.service.user.action

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import user.external.AuthenticationRequestDto
import user.external.TokenDto

@Service
class LoginUserAction {
    operator fun invoke(request: AuthenticationRequestDto): TokenDto {
        return TokenDto("token")
    }
}