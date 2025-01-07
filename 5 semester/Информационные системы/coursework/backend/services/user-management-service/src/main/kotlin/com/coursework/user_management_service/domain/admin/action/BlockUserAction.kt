package com.coursework.user_management_service.domain.admin.action

import admin.external.AdminActionOnUserRequestDto
import com.coursework.user_management_service.infrastructure.persistence.AdminActionOnUserLogRepository
import org.springframework.stereotype.Service

@Service
class BlockUserAction(
    private val repository: AdminActionOnUserLogRepository
) {
    operator fun invoke(request: AdminActionOnUserRequestDto) {

    }
}