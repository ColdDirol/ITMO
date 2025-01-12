package com.coursework.account_management_service.domain.bank_account.action

import com.coursework.account_management_service.domain.bank_account.toDto
import com.coursework.account_management_service.infrastructure.model.BankAccountEntity
import com.coursework.account_management_service.infrastructure.persistence.BankAccountRepository
import com.coursework.account_management_service.infrastructure.persistence.CurrencyRepository
import com.coursework.account_management_service.infrastructure.persistence.user.UserMainInfoRepository
import external.BankAccountDto
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CreateBankAccountAction(
    private val repository: BankAccountRepository,
    private val userRepository: UserMainInfoRepository,
    private val currencyRepository: CurrencyRepository,
) {
    operator fun invoke(dto: BankAccountDto): BankAccountDto {
        val savedBankAccount = repository.save(
            BankAccountEntity(
                id = 0,
                userId = userRepository.userByEmail(dto.email)!!.id ,
                name = dto.name,
                balance = BigDecimal(0),
                currency = currencyRepository.findByShortName(
                    dto.currencyShortName
                ) ?: throw IllegalStateException("Currency not found"),
            )
        )

        return savedBankAccount.toDto(
            userRepository.userByEmail(dto.email)
                ?: throw EntityNotFoundException("No user with id ${savedBankAccount.userId}")
        )
    }
}