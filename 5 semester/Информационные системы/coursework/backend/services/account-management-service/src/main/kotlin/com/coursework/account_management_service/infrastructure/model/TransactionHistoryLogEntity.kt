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
    var date: LocalDateTime,
    @Column(name = "user_id", nullable = false)
    var userId: Long,
    var action: ActionTypes,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", nullable = false)
    var bankAccount: BankAccountEntity,
    var sum: BigDecimal,
)