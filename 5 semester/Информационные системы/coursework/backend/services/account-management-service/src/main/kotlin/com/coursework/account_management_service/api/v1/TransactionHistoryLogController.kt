package com.coursework.account_management_service.api.v1

import com.coursework.account_management_service.domain.TransactionHistoryLogService
import com.coursework.account_management_service.infrastructure.model.TransactionHistoryLogEntity
import external.TransactionHistoryLogRequest
import org.springframework.core.io.InputStreamResource
import org.springframework.data.domain.Page
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter

@RestController
@RequestMapping("/api/v1/transaction-history-log")
class TransactionHistoryLogController(
    private val service: TransactionHistoryLogService,
) {

    @PostMapping
    fun getTransactionHistoryLogs(
        @RequestBody dto: TransactionHistoryLogRequest
    ): Page<TransactionHistoryLogEntity> = service.getTransactionHistoryLogsByUserEmail(dto)

    @PostMapping("/export")
    fun exportTransactionHistoryLogs(
        @RequestBody dto: TransactionHistoryLogRequest
    ): ResponseEntity<StreamingResponseBody> {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_OCTET_STREAM
            setContentDispositionFormData("attachment", "transaction_logs.csv")
        }

        // Используем StreamingResponseBody для прямой записи данных в поток
        val streamingResponseBody = StreamingResponseBody { outputStream ->
            // Передача потока в сервис для записи CSV
            OutputStreamWriter(outputStream, Charsets.UTF_8).use { writer ->
                try {
                    service.exportTransactionHistoryToCsv(writer, dto)
                } catch (e: Exception) {
                    throw RuntimeException("Ошибка при создании CSV-файла", e)
                }
            }
        }

        return ResponseEntity
            .ok()
            .headers(headers)
            .body(streamingResponseBody)
    }

}