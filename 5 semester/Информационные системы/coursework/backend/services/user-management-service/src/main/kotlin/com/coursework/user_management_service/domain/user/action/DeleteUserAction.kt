package com.coursework.user_management_service.domain.user.action

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteUserAction {
    operator fun invoke(userId: Long) {

    }
}