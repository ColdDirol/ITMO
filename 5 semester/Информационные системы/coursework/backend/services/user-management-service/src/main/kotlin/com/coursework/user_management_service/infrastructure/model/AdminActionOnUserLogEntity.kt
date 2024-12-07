package com.coursework.user_management_service.infrastructure.model

import admin.external.ActionType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "administrator_action_with_user_log")
class AdminActionOnUserLogEntity(
    @Id
    var id: Long,
    var date: LocalDateTime,
    @ManyToOne
    var administrator: UsersEntity,
    var action: ActionType,
    @ManyToOne
    var user: UsersEntity
)