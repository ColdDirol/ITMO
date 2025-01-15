package com.coursework.user_management_service.domain.admin_action.action

import admin.external.ActionType
import admin.external.AdminActionOnUserRequestDto
import com.coursework.user_management_service.infrastructure.model.AdminActionOnUserLogEntity
import com.coursework.user_management_service.infrastructure.persistence.AdminActionOnUserLogRepository
import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import internal.UserStatusType
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
open class UnfrozeUserAction(
    private val userRepository: UserRepository,
    private val adminActionOnUserLogRepository: AdminActionOnUserLogRepository,
) {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    open operator fun invoke(request: AdminActionOnUserRequestDto) {
        val user = userRepository.findById(request.userId)
            .orElseThrow { EntityNotFoundException("User not found with id: ${request.userId}") }

        user.status = UserStatusType.ACTIVE

        userRepository.save(user)

        val administratorEmail = SecurityContextHolder.getContext().authentication?.name ?: throw IllegalArgumentException("User not found")
        val administrator = userRepository.findByEmail(administratorEmail)
            .orElseThrow { EntityNotFoundException("User not found with email: $administratorEmail") }

        adminActionOnUserLogRepository.save(
            AdminActionOnUserLogEntity(
                id = 0,
                date = LocalDateTime.now(),
                administrator = administrator,
                action = ActionType.UNBLOCKED,
                user = user,
            )
        )
    }
}