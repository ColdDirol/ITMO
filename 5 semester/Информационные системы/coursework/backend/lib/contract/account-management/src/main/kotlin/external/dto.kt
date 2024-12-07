package external

import com.newdex.services.contract.common.external.UserMainInfoDto

data class CurrencyDto(
    val id: Long,
    val name: String,
    val shortName: String,
)

data class UserAccountDto(
    val id: Long,
    val user: UserMainInfoDto,
    val name: String,
    val balance: String, // Long Long Long Long
    val currency: String,
)