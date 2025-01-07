package com.coursework.account_management_service.infrastructure.model

import internal.ExchangeRateStatus
import jakarta.persistence.*

@Entity
@Table(name = "currency_exchange_rate")
class CurrencyExchangeRateEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(name = "currency_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var currency: CurrencyEntity,
    var status: ExchangeRateStatus,
)