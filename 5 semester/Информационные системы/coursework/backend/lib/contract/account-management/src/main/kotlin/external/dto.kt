package external

import com.newdex.services.contract.common.external.UserMainInfoDto
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