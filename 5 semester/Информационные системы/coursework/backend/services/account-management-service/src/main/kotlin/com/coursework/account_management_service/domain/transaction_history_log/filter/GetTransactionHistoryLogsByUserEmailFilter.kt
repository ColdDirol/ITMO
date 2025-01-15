package com.coursework.account_management_service.domain.transaction_history_log.filter

import com.coursework.account_management_service.domain.BankAccountService
import com.coursework.account_management_service.infrastructure.model.TransactionHistoryLogEntity
import com.coursework.account_management_service.infrastructure.persistence.TransactionHistoryLogRepository
import external.BankAccountRequestDto
import external.TransactionHistoryLogRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class GetTransactionHistoryLogsByUserEmailFilter(
    private val repository: TransactionHistoryLogRepository,
    private val bankAccountService: BankAccountService,
) {
    operator fun invoke(dto: TransactionHistoryLogRequest): Page<TransactionHistoryLogEntity> {
        val accountIds = bankAccountService.getAllByUserEmail(
            BankAccountRequestDto(
                email = dto.userEmail,
                page = 0,
                size = 10,
            )
        ).map { it.id }

        return repository.findAllByAccountFromInOrAccountToIn(accountIds, accountIds,
            PageRequest.of(
                dto.page,
                dto.size,
                Sort.by(
                    Sort.Order.desc("date")
                )
            )
        )
    }
}