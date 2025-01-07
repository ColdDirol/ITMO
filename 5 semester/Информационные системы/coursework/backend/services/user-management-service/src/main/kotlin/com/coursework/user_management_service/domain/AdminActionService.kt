package com.coursework.user_management_service.domain

import com.coursework.user_management_service.domain.admin.action.BlockUserAction
import com.coursework.user_management_service.domain.admin.action.FrozeUserAction
import com.coursework.user_management_service.domain.admin.action.UnblockUserAction
import com.coursework.user_management_service.domain.admin.action.UnfrozeUserAction
import org.springframework.stereotype.Service

@Service
class AdminActionService(
    val blockUser: BlockUserAction,
    val unblockUser: UnblockUserAction,
    val frozeUser: FrozeUserAction,
    val unfrozeUser: UnfrozeUserAction,
)