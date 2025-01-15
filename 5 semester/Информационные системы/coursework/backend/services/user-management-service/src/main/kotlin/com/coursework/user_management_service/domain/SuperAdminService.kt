package com.coursework.user_management_service.domain

import com.coursework.user_management_service.domain.super_admin.action.DismissAdminAction
import com.coursework.user_management_service.domain.super_admin.action.MakeUserAdminAction
import org.springframework.stereotype.Service

@Service
class SuperAdminService(
    val makeUserAdmin: MakeUserAdminAction,
    val dismissAdmin: DismissAdminAction
)