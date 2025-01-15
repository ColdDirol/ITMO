package com.coursework.user_management_service.domain.super_admin.action

import admin.external.MakeUserAdminRequest
import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import internal.RoleType
import internal.UserStatusType
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
open class MakeUserAdminAction(
    private val userRepository: UserRepository,
) {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    open operator fun invoke(dto: MakeUserAdminRequest) {
        val user = userRepository.findById(dto.id)
            .orElseThrow { EntityNotFoundException("User not found with id: ${dto.id}") }

        user.role = RoleType.ADMIN

        userRepository.save(user)
    }
}