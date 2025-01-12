package com.coursework.account_management_service.infrastructure.persistence

import com.coursework.account_management_service.infrastructure.model.BankAccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BankAccountRepository: JpaRepository<BankAccountEntity, Long> {

    fun findAllByUserId(userId: Long): Optional<List<BankAccountEntity>>
}