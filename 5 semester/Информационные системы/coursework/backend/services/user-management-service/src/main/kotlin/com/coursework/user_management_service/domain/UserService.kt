package com.coursework.user_management_service.domain

import com.coursework.user_management_service.domain.user.filter.GetUserByIdFilter
import com.coursework.user_management_service.domain.user.filter.GetUserByPersonalDataFilter
import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    val getUserByIdFilter: GetUserByIdFilter,
    val getUserByPersonalDataFilter: GetUserByPersonalDataFilter,
)