package user.external

import java.time.LocalDateTime

data class RegisterRequestDto(
    val email: String,
    val password: String,
    val phone: String,
    val name: String,
    val sex: SexType,
    val dateOfBirth: LocalDateTime,
)

data class AuthenticationRequestDto(
    val email: String,
    val password: String,
)

data class UserSearchRequestDto(
    val searchTerm: String,
    val page: Int = 0,
    val size: Int = 10,
)