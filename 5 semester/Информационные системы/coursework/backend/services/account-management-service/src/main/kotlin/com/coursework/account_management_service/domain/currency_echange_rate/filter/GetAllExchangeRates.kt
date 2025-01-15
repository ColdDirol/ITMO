package com.coursework.account_management_service.domain.currency_echange_rate.filter

import com.coursework.account_management_service.infrastructure.model.CurrencyExchangeRateEntity
import com.coursework.account_management_service.infrastructure.persistence.CurrencyExchangeRateRepository
import org.springframework.stereotype.Service

@Service
class GetAllExchangeRates(
    private val repository: CurrencyExchangeRateRepository,
) {
    operator fun invoke(): List<CurrencyExchangeRateEntity> = repository.findAll()
}