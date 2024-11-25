package com.newdex.services.contract.common.external

import java.time.LocalDateTime

data class UserInfoDto(
    val id: String,
    val email: String,
    val username: String,
    val name: String,
    val surname: String,
    val patronymic: String?,
)

data class ModificationInfoDto(
    val creationDate: LocalDateTime,
    val lastModificationDate: LocalDateTime,
    val lastModificationAuthor: UserInfoDto
)

data class GroupGradingPolicyDto(
    val courseId: Long,
    val gradingPolicyId: Long,
    val groupId: Long,
)

data class NamedResourceDto(
    val id: Long,
    val name: String,
)
