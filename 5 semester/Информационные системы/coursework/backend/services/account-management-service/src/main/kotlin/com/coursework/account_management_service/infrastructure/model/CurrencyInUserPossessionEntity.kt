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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    var currency: CurrencyEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", nullable = false)
    var bankAccount: BankAccountEntity,
)