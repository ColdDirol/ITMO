package user.internal

import com.newdex.services.contract.common.external.UserMainInfoDto
import internal.PathDto
import java.time.LocalDateTime

data class UserProfilePicturePathInternalDto(
    val id: Long,
    val date: LocalDateTime,
    val user: UserMainInfoDto,
    val path: PathDto,
)