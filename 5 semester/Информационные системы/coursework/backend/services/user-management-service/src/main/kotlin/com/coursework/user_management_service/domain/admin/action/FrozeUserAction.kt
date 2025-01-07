package com.coursework.user_management_service.domain.admin.action

import admin.external.AdminActionOnUserRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class FrozeUserAction {
    operator fun invoke(request: AdminActionOnUserRequestDto) {

    }
}