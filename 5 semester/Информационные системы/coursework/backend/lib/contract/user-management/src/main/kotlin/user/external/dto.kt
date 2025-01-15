package user.external

import external.UserMainInfoProjection
import java.time.LocalDateTime

data class UserInfoChangeLogDto(
    val id: Long,
    val date: LocalDateTime,
    val user: UserMainInfoProjection,
)