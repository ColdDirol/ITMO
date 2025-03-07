package internal

import external.CurrencyDto
import external.UserMainInfoProjection
import java.math.BigDecimal
import java.time.LocalDateTime

data class AccountMainInfoInternalDto(
    val id: Long,
    val name: String,
    val balance: BigDecimal, // Long Long Long Long
    val currency: CurrencyDto,
)

data class TransactionHistoryLogInternalDto(
    val id: Long,
    val data: LocalDateTime,
    val user: UserMainInfoProjection,
    val action: ActionTypes, // ENUM
    val userAccount: AccountMainInfoInternalDto,
    val sum: BigDecimal, // Long Long Long Long
)

data class CurrencyExchangeRateInternalDto(
    val id: Long,
    val currency: CurrencyDto,
    val status: ExchangeRateStatus, // ENUM
    val coefficient: BigDecimal, // Long Long Long Long
)