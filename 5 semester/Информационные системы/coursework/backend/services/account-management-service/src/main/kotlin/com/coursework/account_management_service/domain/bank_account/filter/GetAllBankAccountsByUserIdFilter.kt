package com.coursework.account_management_service.domain.bank_account.filter

import com.coursework.account_management_service.domain.bank_account.toDto
import com.coursework.account_management_service.infrastructure.persistence.BankAccountRepository
import com.coursework.account_management_service.infrastructure.persistence.user.UserMainInfoRepository
import external.BankAccountDto
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetAllBankAccountsByUserIdFilter(
    private val repository: BankAccountRepository, private val userRepository: UserMainInfoRepository
) {
    operator fun invoke(userId: Long): List<BankAccountDto> = repository.findAllByUserId(userId)
        .orElseThrow { EntityNotFoundException("No bank accounts for user id $userId") }.map {
            it.toDto(
                user = userRepository.userById(it.userId)
                    .orElseThrow { EntityNotFoundException("No users with id ${it.userId}") })
        }
}