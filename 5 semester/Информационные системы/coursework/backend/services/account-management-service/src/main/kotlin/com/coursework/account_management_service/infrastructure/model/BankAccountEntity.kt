package com.coursework.account_management_service.infrastructure.model

import external.BankAccountDto
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "bank_account")
class BankAccountEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(name = "user_id", nullable = false)
    var userId: Long,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var balance: BigDecimal,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    var currency: CurrencyEntity,
)