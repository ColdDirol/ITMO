package user.internal

import com.newdex.services.contract.common.external.UserMainInfoDto
import com.newdex.services.contract.common.internal.PathDto
import java.time.LocalDateTime

data class UserProfilePicturePathInternalDto(
    val id: Long,
    val date: LocalDateTime,
    val user: UserMainInfoDto,
    val path: PathDto,
)