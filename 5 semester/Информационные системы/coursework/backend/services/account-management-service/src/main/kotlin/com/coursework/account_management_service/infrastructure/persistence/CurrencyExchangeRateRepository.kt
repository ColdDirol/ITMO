package com.coursework.account_management_service.infrastructure.persistence

import com.coursework.account_management_service.infrastructure.model.CurrencyExchangeRateEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CurrencyExchangeRateRepository: JpaRepository<CurrencyExchangeRateEntity, Long> {

    fun findByCurrency_Id(currencyId: Long): Optional<CurrencyExchangeRateEntity>
}