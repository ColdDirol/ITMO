package com.coursework.user_management_service.api.http

import com.coursework.user_management_service.domain.service.UserService
import org.springframework.web.bind.annotation.*
import user.external.AuthenticationRequestDto
import user.external.RegisterRequestDto
import user.external.TokenDto

@RestController
@RequestMapping("/api/v1/user")
class UserControllerV1(
    private val service: UserService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody dto: RegisterRequestDto
    ): TokenDto = service.registerUser(dto)

    @PostMapping("/login")
    fun login(
        @RequestBody dto: AuthenticationRequestDto
    ): TokenDto = service.loginUser(dto)

    @DeleteMapping("/delete/{userId}")
    fun delete(
        @PathVariable("userId") userId: Long
    ) {
        service.deleteUser(userId)
    }
}