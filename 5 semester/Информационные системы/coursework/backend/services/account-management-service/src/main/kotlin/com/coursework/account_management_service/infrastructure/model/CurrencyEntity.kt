package com.coursework.account_management_service.infrastructure.model

import jakarta.persistence.*

@Entity
@Table(name = "currency")
class CurrencyEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    var shortName: String,
)