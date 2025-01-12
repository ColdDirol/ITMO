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
    var email: String,
    var password: String,
    var role: RoleType,
    var phone: String,
    var name: String,
    var sex: SexType,
    var dateOfBirth: LocalDateTime,
    var status: UserStatusType,
)