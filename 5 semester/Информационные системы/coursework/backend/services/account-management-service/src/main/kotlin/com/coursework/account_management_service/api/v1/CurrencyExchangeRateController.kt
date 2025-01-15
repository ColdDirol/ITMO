package com.coursework.account_management_service.api.v1

import com.coursework.account_management_service.domain.CurrencyExchangeRateService
import com.coursework.account_management_service.infrastructure.model.CurrencyExchangeRateEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/currency/exchange-rates")
class CurrencyExchangeRateController(
    private val service: CurrencyExchangeRateService,
) {

    @GetMapping
    fun getAllExchangeRates(): List<CurrencyExchangeRateEntity> = service.getAllExchangeRates()
}