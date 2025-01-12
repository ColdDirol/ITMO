package com.coursework.account_management_service.domain.bank_account.action

import com.coursework.account_management_service.infrastructure.persistence.BankAccountRepository
import org.springframework.stereotype.Service

@Service
class DeleteBankAccountAction(
    private val repository: BankAccountRepository,
) {
    operator fun invoke(id: Long) {
        repository.deleteById(id)
    }
}