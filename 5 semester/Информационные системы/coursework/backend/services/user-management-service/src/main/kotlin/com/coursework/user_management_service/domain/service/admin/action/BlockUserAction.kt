package com.coursework.user_management_service.domain.service.actions

import admin.external.AdminActionOnUserRequestDto
import com.coursework.user_management_service.infrastructure.repo.AdminActionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class BlockUserAction(
    private val repository: AdminActionRepository
) {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    operator fun invoke(request: AdminActionOnUserRequestDto) {
    }
}