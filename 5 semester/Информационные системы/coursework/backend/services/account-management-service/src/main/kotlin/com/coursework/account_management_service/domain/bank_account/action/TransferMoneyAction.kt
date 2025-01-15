package com.coursework.account_management_service.domain.bank_account.action

import com.coursework.account_management_service.infrastructure.model.TransactionHistoryLogEntity
import com.coursework.account_management_service.infrastructure.persistence.BankAccountRepository
import com.coursework.account_management_service.infrastructure.persistence.CurrencyExchangeRateRepository
import com.coursework.account_management_service.infrastructure.persistence.TransactionHistoryLogRepository
import external.TransferDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
open class TransferMoneyAction(
    private val repository: BankAccountRepository,
    private val transactionHistoryLogRepository: TransactionHistoryLogRepository,
    private val currencyExchangeRateRepository: CurrencyExchangeRateRepository
) {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    open operator fun invoke(dto: TransferDto) {
        val sender = repository.findById(dto.idFrom)
            .orElseThrow { IllegalArgumentException("There is no sender with bank account id ${dto.idFrom}") }
        val receiver = repository.findById(dto.idTo)
            .orElseThrow { IllegalArgumentException("There is no receiver with bank account id ${dto.idTo}") }

        if (sender.balance < dto.amount) {
            throw IllegalArgumentException("There isn't enough balance to transfer, current balance: ${sender.balance}")
        }

        val senderRate = currencyExchangeRateRepository.findById(sender.currency.id)
            .orElseThrow { IllegalArgumentException("No exchange rate found for currency ${sender.currency.shortName}") }
            .coefficient
        val receiverRate = currencyExchangeRateRepository.findById(receiver.currency.id)
            .orElseThrow { IllegalArgumentException("No exchange rate found for currency ${receiver.currency.shortName}") }
            .coefficient

        // Convert through USD as base currency
        // 1. Convert sender amount to USD
        val amountInUsd = dto.amount * senderRate.toBigDecimal()
        // 2. Convert USD amount to receiver's currency
        val amountInReceiverCurrency = amountInUsd / receiverRate.toBigDecimal()

        sender.balance -= dto.amount
        receiver.balance += amountInReceiverCurrency

        transactionHistoryLogRepository.save(
            TransactionHistoryLogEntity(
                id = 0,
                date = LocalDateTime.now(),
                accountFrom = sender.id,
                accountTo = receiver.id,
                senderCurrency = sender.currency.shortName,
                receiverCurrency = receiver.currency.shortName,
                amountInSenderCurrency = dto.amount,
                amountInReceiverCurrency = amountInReceiverCurrency,
            )
        )

        repository.save(sender)
        repository.save(receiver)
    }
}