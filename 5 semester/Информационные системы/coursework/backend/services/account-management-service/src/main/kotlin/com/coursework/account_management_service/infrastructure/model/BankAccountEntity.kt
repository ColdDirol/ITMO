package com.coursework.account_management_service.infrastructure.model

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
    var name: String,
    var balance: BigDecimal,
    @Column(name = "currency_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var currency: CurrencyEntity,
)