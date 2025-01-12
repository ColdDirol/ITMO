package com.coursework.user_management_service.api.v1

import admin.external.AdminActionOnUserRequestDto
import com.coursework.user_management_service.domain.AdminActionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/admin/actions")
class AdminActionController(
    private val service: AdminActionService
) {

    @PostMapping("/block")
    fun blockUser(
        @RequestBody dto: AdminActionOnUserRequestDto
    ) {
        service.blockUser(dto)
    }

    @PostMapping("/unblock")
    fun unblockUser(
        @RequestBody dto: AdminActionOnUserRequestDto
    ) {
        service.unblockUser(dto)
    }

    @PostMapping("/froze")
    fun frozeUser(
        @RequestBody dto: AdminActionOnUserRequestDto
    ) {
        service.frozeUser(dto)
    }

    @PostMapping("/unfroze")
    fun unfrozeUser(
        @RequestBody dto: AdminActionOnUserRequestDto
    ) {
        service.unfrozeUser(dto)
    }

    @PostMapping("/delete")
    fun deleteUser(
        @RequestBody dto: AdminActionOnUserRequestDto
    ) {
        service.deleteUser(dto)
    }
}