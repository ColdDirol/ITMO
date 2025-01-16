package com.coursework.user_management_service.infrastructure.model

import internal.RoleType
import internal.UserStatusType
import jakarta.persistence.*
import user.external.SexType
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class UsersEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(nullable = false)
    var email: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    var role: RoleType,
    @Column(nullable = false)
    var phone: String,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var sex: SexType,
    @Column(nullable = false)
    var dateOfBirth: LocalDateTime,
    @Column(nullable = false)
    var status: UserStatusType,
)