package com.coursework.user_management_service.infrastructure.model

import admin.external.ActionType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "administrator_action_on_user_log")
class AdminActionOnUserLogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var date: LocalDateTime,
    @ManyToOne
    var administrator: UsersEntity,
    var action: ActionType,
    @ManyToOne
    var user: UsersEntity
)