package internal

import com.newdex.services.contract.common.external.UserMainInfoDto
import external.CurrencyDto
import java.time.LocalDateTime

data class AccountMainInfoInternalDto(
    val id: Long,
    val name: String,
    val balance: String, // Long Long Long Long
    val currency: CurrencyDto,
)

data class TransactionHistoryLogInternalDto(
    val id: Long,
    val data: LocalDateTime,
    val user: UserMainInfoDto,
    val action: ActionTypes, // ENUM
    val userAccount: AccountMainInfoInternalDto,
    val sum: String, // Long Long Long Long
)

data class CurrencyInUserPossessionInternalDto(
    val id: Long,
    val user: UserMainInfoDto,
    val currency: CurrencyDto,
    val userAccount: AccountMainInfoInternalDto,
)

data class CurrencyExchangeRateInternalDto(
    val id: Long,
    val currency: CurrencyDto,
    val status: ExchangeRateStatus, // ENUM
    val coefficient: String, // Long Long Long Long
)