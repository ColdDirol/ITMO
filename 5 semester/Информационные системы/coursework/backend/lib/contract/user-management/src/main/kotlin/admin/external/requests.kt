package admin.external

data class AdminActionOnUserRequestDto(
    val userId: Long,
    val cause: String,
)