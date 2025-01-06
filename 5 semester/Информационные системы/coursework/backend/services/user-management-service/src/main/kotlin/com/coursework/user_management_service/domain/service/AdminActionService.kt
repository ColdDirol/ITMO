package com.coursework.user_management_service.domain.service

import com.coursework.user_management_service.domain.service.actions.BlockUserAction
import com.coursework.user_management_service.domain.service.actions.FrozeUserAction
import com.coursework.user_management_service.domain.service.actions.UnblockUserAction
import com.coursework.user_management_service.domain.service.admin.action.UnfrozeUserAction
import org.springframework.stereotype.Service

@Service
class AdminActionService(
    val blockUser: BlockUserAction,
    val unblockUser: UnblockUserAction,
    val frozeUser: FrozeUserAction,
    val unfrozeUser: UnfrozeUserAction,
)