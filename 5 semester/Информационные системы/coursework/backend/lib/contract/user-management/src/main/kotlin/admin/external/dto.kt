package admin.external

import external.UserMainInfoDto
import external.UserMainInfoProjection
import java.time.LocalDateTime

data class AdministrationActionWithUserLog(
    val id: Long,
    val date: LocalDateTime,
    val administrator: UserMainInfoProjection,
    val action: ActionType, // ENUM
    val user: UserMainInfoProjection,
)

data class AdministrationActionWithUserLogDto(
    val id: Long,
    val date: LocalDateTime,
    val administrator: UserMainInfoDto,
    val action: ActionType, // ENUM
    val user: UserMainInfoDto,
)

data class AdministrationActionHistoryRequest(
    val page: Int = 0,
    val size: Int = 10,
)

data class MakeUserAdminRequest(
    val id: Long,
)