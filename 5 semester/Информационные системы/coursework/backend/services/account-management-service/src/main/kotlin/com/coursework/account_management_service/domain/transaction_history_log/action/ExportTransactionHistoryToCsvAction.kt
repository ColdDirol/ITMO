package com.coursework.account_management_service.domain.transaction_history_log.action

import com.coursework.account_management_service.domain.BankAccountService
import com.coursework.account_management_service.infrastructure.persistence.TransactionHistoryLogRepository
import com.opencsv.CSVWriter
import external.BankAccountRequestDto
import external.TransactionHistoryLogRequest
import org.springframework.stereotype.Service
import java.io.Writer

@Service
class ExportTransactionHistoryToCsvAction(
    private val repository: TransactionHistoryLogRepository,
    private val bankAccountService: BankAccountService,
) {
    operator fun invoke(writer: Writer, dto: TransactionHistoryLogRequest) {
        val csvWriter = CSVWriter(writer)
        val header = arrayOf(
            "ID", "Date", "Account From", "Account To",
            "Sender Currency", "Receiver Currency",
            "Amount In Sender Currency", "Amount In Receiver Currency"
        )
        csvWriter.writeNext(header)

        // Получение данных из базы
        val accountIds = bankAccountService.getAllByUserEmail(
            BankAccountRequestDto(
                email = dto.userEmail,
                page = 0,
                size = 10,
            )
        ).map { it.id }

        val logs = repository.findAllByAccountFromInOrAccountToIn(accountIds, accountIds)

        logs.forEach { log ->
            csvWriter.writeNext(
                arrayOf(
                    log.id.toString(),
                    log.date.toString(),
                    log.accountFrom.toString(),
                    log.accountTo.toString(),
                    log.senderCurrency,
                    log.receiverCurrency,
                    log.amountInSenderCurrency.toPlainString(),
                    log.amountInReceiverCurrency.toPlainString()
                )
            )
        }
        csvWriter.close()
    }
}