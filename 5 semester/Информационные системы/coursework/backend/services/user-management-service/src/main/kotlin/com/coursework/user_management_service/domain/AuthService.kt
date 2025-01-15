package com.coursework.user_management_service.domain

import com.coursework.user_management_service.domain.auth.action.DeleteUserAction
import com.coursework.user_management_service.domain.auth.action.LoginUserAction
import com.coursework.user_management_service.domain.auth.action.RegisterUserAction
import org.springframework.stereotype.Service

@Service
class AuthService(
    val registerUser: RegisterUserAction,
    val loginUser: LoginUserAction,
    val deleteUser: DeleteUserAction,
)