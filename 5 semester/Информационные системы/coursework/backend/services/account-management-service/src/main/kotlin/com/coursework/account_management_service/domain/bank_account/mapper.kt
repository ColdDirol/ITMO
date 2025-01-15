package com.coursework.account_management_service.domain.bank_account

import com.coursework.account_management_service.infrastructure.model.BankAccountEntity
import external.BankAccountDto
import external.UserMainInfoProjection

fun BankAccountEntity.toDto(
    user: UserMainInfoProjection
) = BankAccountDto(
    id = this.id,
    email = user.email,
    name = this.name,
    balance = this.balance,
    currencyShortName = this.currency.shortName,
)