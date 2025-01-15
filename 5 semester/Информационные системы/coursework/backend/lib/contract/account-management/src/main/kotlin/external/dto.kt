package external

import java.math.BigDecimal

data class CurrencyDto(
    val id: Long,
    val name: String,
    val shortName: String,
)

data class BankAccountDto(
    val id: Long,
    val email: String,
    val name: String,
    val balance: BigDecimal,
    val currencyShortName: String,
)

data class TransferDto(
    val idFrom: Long,
    val idTo: Long,
    val amount: BigDecimal,
)

data class BankAccountRequestDto(
    val email: String,
    val page: Int = 0,
    val size: Int = 10,
)

data class TransactionHistoryLogRequest(
    val userEmail: String,
    val page: Int = 0,
    val size: Int = 10,
)