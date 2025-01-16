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
    @Column(nullable = false)
    var date: LocalDateTime,
    @Column(nullable = false)
    @ManyToOne
    var administrator: UsersEntity,
    @Column(nullable = false)
    var action: ActionType,
    @Column(nullable = false)
    @ManyToOne
    var user: UsersEntity
)