package com.coursework.account_management_service.domain

import com.coursework.account_management_service.domain.currency_echange_rate.filter.GetAllExchangeRates
import org.springframework.stereotype.Service

@Service
class CurrencyExchangeRateService(
    val getAllExchangeRates: GetAllExchangeRates,
)