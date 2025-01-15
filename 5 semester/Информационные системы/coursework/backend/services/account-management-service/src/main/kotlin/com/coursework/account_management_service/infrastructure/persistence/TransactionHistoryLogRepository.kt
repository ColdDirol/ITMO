package com.coursework.account_management_service.infrastructure.persistence

import com.coursework.account_management_service.infrastructure.model.TransactionHistoryLogEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionHistoryLogRepository: JpaRepository<TransactionHistoryLogEntity, Long> {
    fun findAllByAccountFromInOrAccountToIn(accountFromIds: List<Long>, accountToIds: List<Long>, pageable: Pageable): Page<TransactionHistoryLogEntity>
    fun findAllByAccountFromInOrAccountToIn(accountFromIds: List<Long>, accountToIds: List<Long>): List<TransactionHistoryLogEntity>
}