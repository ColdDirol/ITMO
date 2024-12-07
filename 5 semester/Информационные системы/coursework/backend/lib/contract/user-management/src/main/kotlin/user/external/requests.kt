package user.external

import java.time.LocalDateTime

data class RegisterRequestDto(
    val email: String,
    val password: String,
    val phone: String,
    val fullName: String,
    val dateOfBirth: LocalDateTime,
)

data class AuthenticationRequestDto(
    val email: String,
    val password: String,
)