package com.coursework.user_management_service.domain.admin_action.filter

import admin.external.AdministrationActionHistoryRequest
import admin.external.AdministrationActionWithUserLog
import admin.external.AdministrationActionWithUserLogDto
import com.coursework.user_management_service.infrastructure.persistence.AdminActionOnUserLogRepository
import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import external.UserMainInfoDto
import external.UserMainInfoProjection
import internal.RoleType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class GetAllAdminActionHistoryFilter(
    private val repository: AdminActionOnUserLogRepository
) {
    operator fun invoke(
        dto: AdministrationActionHistoryRequest
    ): Page<AdministrationActionWithUserLogDto> {
        val history = repository.findAll(
            PageRequest.of(dto.page, dto.size)
        )

        return PageImpl(
            history.content.map { log ->
                AdministrationActionWithUserLogDto(
                    id = log.id,
                    date = log.date,
                    administrator = UserMainInfoDto(
                        id = log.administrator.id,
                        email = log.administrator.email,
                        name = log.administrator.name,
                    ),
                    action = log.action,
                    user = UserMainInfoDto(
                        id = log.user.id,
                        email = log.user.email,
                        name = log.user.name,
                    )
                )
            }
        )
    }
}