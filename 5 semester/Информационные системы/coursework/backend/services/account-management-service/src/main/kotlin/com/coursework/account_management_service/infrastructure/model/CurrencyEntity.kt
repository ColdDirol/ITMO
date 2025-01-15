package com.coursework.account_management_service.infrastructure.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "currency")
class CurrencyEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    var shortName: String,
)