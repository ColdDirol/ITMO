package com.coursework.account_management_service.domain.bank_account.action

import com.coursework.account_management_service.infrastructure.model.BankAccountEntity
import com.coursework.account_management_service.infrastructure.persistence.BankAccountRepository
import external.TransferDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
open class TransferMoneyAction(
    private val repository: BankAccountRepository,
) {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    open operator fun invoke(dto: TransferDto) {
        val sender = repository.findById(dto.idFrom)
            .orElseThrow { IllegalArgumentException("There is no sender with bank account id ${dto.idFrom}") }
        val receiver = repository.findById(dto.idTo)
            .orElseThrow { IllegalArgumentException("There is no receiver with bank account id ${dto.idTo}") }

        if (sender.balance >= dto.amount)
            throw IllegalArgumentException("There isn't enough balance to transfer to ${sender.balance}")

        sender.balance -= dto.amount
        receiver.balance += dto.amount

        repository.save(sender)
        repository.save(receiver)
    }
}