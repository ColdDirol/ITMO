package com.coursework.user_management_service.infrastructure.persistence

import com.coursework.user_management_service.infrastructure.model.UsersEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminRepository: JpaRepository<com.coursework.user_management_service.infrastructure.model.UsersEntity, Long> {

    @Query(
        """
            SELECT DISTINCT c.short_name
            FROM bank_account b
            JOIN currency c ON b.currency_id = c.id
            WHERE b.user_id = :userId
        """, nativeQuery = true
    )
    fun getUserCurrencyNames(userId: Long): List<String>
}