package com.coursework.user_management_service.api.v1

import com.coursework.user_management_service.domain.UserService
import external.UserInfoDto
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import user.external.UserSearchRequestDto

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val service: UserService
) {

    @PostMapping("/search")
    fun searchUsers(
        @RequestBody dto: UserSearchRequestDto
    ): Page<UserInfoDto> {
        return service.getUserByPersonalDataFilter(dto)
    }
}