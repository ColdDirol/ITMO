package com.coursework.account_management_service.infrastructure.model

import internal.ActionTypes
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transaction_history_log")
class TransactionHistoryLogEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var date: LocalDateTime,

    @Column(name = "account_from", nullable = false)
    var accountFrom: Long,

    @Column(name = "account_to", nullable = false)
    var accountTo: Long,

    @Column(nullable = false)
    var senderCurrency: String,

    @Column(nullable = false)
    var receiverCurrency: String,

    @Column(nullable = false)
    var amountInSenderCurrency: BigDecimal,

    @Column(nullable = false)
    var amountInReceiverCurrency: BigDecimal,
)