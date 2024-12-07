package user.external

import com.newdex.services.contract.common.external.UserMainInfoDto
import java.time.LocalDateTime

data class UserInfoChangeLogDto(
    val id: Long,
    val date: LocalDateTime,
    val user: UserMainInfoDto,
)