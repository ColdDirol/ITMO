package com.coursework.account_management_service.infrastructure.model

import internal.ExchangeRateStatus
import jakarta.persistence.*

@Entity
@Table(name = "currency_exchange_rate")
class CurrencyExchangeRateEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_id", nullable = false)
    var currency: CurrencyEntity,
    @Column(nullable = false)
    var coefficient: Double,
    @Column(nullable = false)
    var status: ExchangeRateStatus,
)