package com.newdex.services.contract.common.external

import internal.RoleType
import internal.UserStatusType
import java.time.LocalDateTime

data class UserInfoDto(
    val id: Long,
    val email: String,
    val role: RoleType,
    val phone: String,
    val name: String,
    val dateOfBirth: LocalDateTime,
    val status: UserStatusType,
)

data class UserMainInfoDto(
    val id: Long,
    val email: String,
    val name: String,
)