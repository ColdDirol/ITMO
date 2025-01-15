package user.internal

import external.UserMainInfoProjection
import internal.PathDto
import java.time.LocalDateTime

data class UserProfilePicturePathInternalDto(
    val id: Long,
    val date: LocalDateTime,
    val user: UserMainInfoProjection,
    val path: PathDto,
)