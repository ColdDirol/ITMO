package com.coursework.account_management_service.infrastructure.model

import jakarta.persistence.*

@Entity
@Table(name = "currency_in_user_possession")
class CurrencyInUserPossessionEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(name = "user_id", nullable = false)
    var userId: Long,
    @Column(name = "currency_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var currency: CurrencyEntity,
    @Column(name = "bank_account_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var bankAccount: BankAccountEntity,
)