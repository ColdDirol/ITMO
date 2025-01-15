package external

import internal.RoleType
import internal.UserStatusType
import java.time.LocalDateTime

data class UserInfoDto(
    val id: Long,
    val email: String,
    val role: RoleType,
    val phone: String,
    val name: String,
    val sex: String, // Enum
    val dateOfBirth: LocalDateTime,
    val status: UserStatusType,
)

interface UserMainInfoProjection {
    val id: Long
    val email: String
    val name: String
}

data class UserMainInfoDto(
    val id: Long,
    val email: String,
    val name: String,
)