package com.coursework.account_management_service.domain.bank_account.filter

import com.coursework.account_management_service.domain.bank_account.toDto
import com.coursework.account_management_service.infrastructure.persistence.BankAccountRepository
import com.coursework.account_management_service.infrastructure.persistence.user.UserMainInfoRepository
import external.BankAccountDto
import external.BankAccountRequestDto
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetAllBankAccountsByUserEmailFilter(
    private val repository: BankAccountRepository,
    private val userRepository: UserMainInfoRepository
) {
    operator fun invoke(dto: BankAccountRequestDto): List<BankAccountDto> {
        val user = userRepository.userByEmail(dto.email)
            ?: throw EntityNotFoundException("User not found by email: ${dto.email}")
        return repository.findAllByUserId(user.id)
            .orElseThrow { EntityNotFoundException("No bank accounts for user id ${user.id}") }.map {
            it.toDto(
                user = userRepository.userById(it.userId)
                    .orElseThrow { EntityNotFoundException("No users with id ${it.userId}") })
        }
    }
}