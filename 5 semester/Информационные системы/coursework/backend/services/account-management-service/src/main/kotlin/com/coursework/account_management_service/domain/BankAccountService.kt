package com.coursework.account_management_service.domain

import com.coursework.account_management_service.domain.bank_account.action.CreateBankAccountAction
import com.coursework.account_management_service.domain.bank_account.action.DeleteBankAccountAction
import com.coursework.account_management_service.domain.bank_account.action.TransferMoneyAction
import com.coursework.account_management_service.domain.bank_account.filter.GetAllBankAccountsByUserIdFilter
import com.coursework.account_management_service.domain.bank_account.filter.GetBankAccountByIdFilter
import org.springframework.stereotype.Service

@Service
class BankAccountService(
    // CRUD
    val create: CreateBankAccountAction,
    val getById: GetBankAccountByIdFilter,
    val getAllByUserId: GetAllBankAccountsByUserIdFilter,
    val delete: DeleteBankAccountAction,

    val transfer: TransferMoneyAction,
)