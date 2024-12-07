package com.coursework.user_management_service.domain.service.user.action

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteUserAction {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    operator fun invoke(userId: Long) {

    }
}