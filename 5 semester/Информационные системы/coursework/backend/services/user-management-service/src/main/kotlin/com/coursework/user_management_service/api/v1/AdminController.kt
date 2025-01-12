package com.coursework.user_management_service.api.v1

import com.coursework.user_management_service.domain.AdminService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin")
class AdminController(
    private val service: AdminService,
) {

    @GetMapping("/{userId}/currencies")
    fun getUserCurrencies(
        @PathVariable userId: Long,
    ): List<String> {
        return service.getUserCurrencies(userId);
    }
}