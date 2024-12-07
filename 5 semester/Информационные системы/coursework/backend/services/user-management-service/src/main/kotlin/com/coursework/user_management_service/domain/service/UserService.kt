package com.coursework.user_management_service.domain.service

import com.coursework.user_management_service.domain.service.user.action.DeleteUserAction
import com.coursework.user_management_service.domain.service.user.action.LoginUserAction
import com.coursework.user_management_service.domain.service.user.action.RegisterUserAction
import org.springframework.stereotype.Service

@Service
class UserService(
    val registerUser: RegisterUserAction,
    val loginUser: LoginUserAction,
    val deleteUser: DeleteUserAction,
)