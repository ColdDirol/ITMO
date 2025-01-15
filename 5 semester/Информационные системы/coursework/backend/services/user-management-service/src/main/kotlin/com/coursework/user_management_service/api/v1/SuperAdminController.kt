package com.coursework.user_management_service.api.v1

import admin.external.MakeUserAdminRequest
import com.coursework.user_management_service.domain.SuperAdminService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin/super-admin")
class SuperAdminController(
    private val service: SuperAdminService,
) {

    @PostMapping("/make-user-admin")
    fun makeUserAdmin(
        @RequestBody dto: MakeUserAdminRequest
    ) {
        return service.makeUserAdmin(dto);
    }

    @PostMapping("/dismiss-user")
    fun dismissAdmin(
        @RequestBody dto: MakeUserAdminRequest
    ) {
        return service.dismissAdmin(dto);
    }
}