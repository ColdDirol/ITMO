package admin.external

import com.newdex.services.contract.common.external.UserMainInfoDto
import java.time.LocalDateTime

data class AdministrationActionWithUserLog(
    val id: Long,
    val date: LocalDateTime,
    val administrator: UserMainInfoDto,
    val action: ActionType, // ENUM
    val user: UserMainInfoDto,
)