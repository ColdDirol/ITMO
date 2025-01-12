package com.coursework.user_management_service.domain.user.action

import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import internal.UserStatusType
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class DeleteUserAction(
    private val repository: UserRepository,
) {
    operator fun invoke(userId: Long) {
        val user = repository.findById(userId)
            .orElseThrow { EntityNotFoundException("User not found with id: $userId") }

        user.status = UserStatusType.DELETED

        repository.save(user)
    }
}