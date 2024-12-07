package admin.external

data class AdminActionOnUserRequestDto(
    val userId: Long,
    val action: ActionType,
    val cause: String,
)