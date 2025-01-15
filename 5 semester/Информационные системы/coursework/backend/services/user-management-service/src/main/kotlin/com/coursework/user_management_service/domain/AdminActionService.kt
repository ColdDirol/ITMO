package com.coursework.user_management_service.domain

import com.coursework.user_management_service.domain.admin_action.action.*
import com.coursework.user_management_service.domain.admin_action.filter.GetAllAdminActionHistoryFilter
import org.springframework.stereotype.Service

@Service
class AdminActionService(
    val blockUser: BlockUserAction,
    val unblockUser: UnblockUserAction,
    val frozeUser: FrozeUserAction,
    val unfrozeUser: UnfrozeUserAction,

    val deleteUser: DeleteUserAdminAction,
    val activateUser: ActivateUserAdminAction,

    val getAllAdminActionHistory: GetAllAdminActionHistoryFilter
)