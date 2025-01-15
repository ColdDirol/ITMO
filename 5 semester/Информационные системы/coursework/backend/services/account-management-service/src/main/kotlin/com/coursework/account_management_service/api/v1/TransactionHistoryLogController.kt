package com.coursework.account_management_service.api.v1

import com.coursework.account_management_service.domain.TransactionHistoryLogService
import com.coursework.account_management_service.infrastructure.model.TransactionHistoryLogEntity
import external.TransactionHistoryLogRequest
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/transaction-history-log")
class TransactionHistoryLogController(
    private val service: TransactionHistoryLogService,
) {

    @PostMapping
    fun getTransactionHistoryLogs(
        @RequestBody dto: TransactionHistoryLogRequest
    ): Page<TransactionHistoryLogEntity> = service.getTransactionHistoryLogsByUserEmail(dto)

}