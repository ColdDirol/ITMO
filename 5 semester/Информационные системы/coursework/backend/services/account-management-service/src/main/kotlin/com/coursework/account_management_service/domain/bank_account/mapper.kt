package com.coursework.account_management_service.domain.bank_account

import com.coursework.account_management_service.infrastructure.model.BankAccountEntity
import com.newdex.services.contract.common.external.UserMainInfoDto
import external.BankAccountDto

fun BankAccountEntity.toDto(
    user: UserMainInfoDto
) = BankAccountDto(
    id = this.id,
    email = user.email,
    name = this.name,
    balance = this.balance,
    currencyShortName = this.currency.shortName,
)