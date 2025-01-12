package com.coursework.account_management_service.domain.bank_account.filter

import com.coursework.account_management_service.domain.bank_account.toDto
import com.coursework.account_management_service.infrastructure.persistence.BankAccountRepository
import com.coursework.account_management_service.infrastructure.persistence.user.UserMainInfoRepository
import external.BankAccountDto
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetBankAccountByIdFilter(
    private val repository: BankAccountRepository,
    private val userRepository: UserMainInfoRepository
) {
    operator fun invoke(id: Long): BankAccountDto {
        val bankAccountEntity = repository
            .findById(id)
            .orElseThrow { EntityNotFoundException("No bank account with id $id") }

        return bankAccountEntity.toDto(
            user = userRepository
                .userById(bankAccountEntity.userId)
                .orElseThrow { EntityNotFoundException("No users with id ${bankAccountEntity.userId}") }
        )
    }
}