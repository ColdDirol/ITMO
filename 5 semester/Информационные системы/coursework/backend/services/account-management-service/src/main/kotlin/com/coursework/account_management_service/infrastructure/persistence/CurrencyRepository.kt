package com.coursework.account_management_service.infrastructure.persistence

import com.coursework.account_management_service.infrastructure.model.CurrencyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyRepository: JpaRepository<CurrencyEntity, Long> {

    fun findByShortName(shortName: String): CurrencyEntity?
}