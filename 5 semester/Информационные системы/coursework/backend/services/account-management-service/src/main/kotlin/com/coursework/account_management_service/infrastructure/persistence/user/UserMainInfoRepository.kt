package com.coursework.account_management_service.infrastructure.persistence.user

import com.coursework.account_management_service.infrastructure.model.BankAccountEntity
import com.newdex.services.contract.common.external.UserMainInfoDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserMainInfoRepository : JpaRepository<BankAccountEntity, Long> {

    @Query(
        """
            SELECT id, email, name 
            FROM users 
            WHERE id = :userId
        """, nativeQuery = true
    )
    fun userById(
        @Param("userId") userId: Long
    ): Optional<UserMainInfoDto>

    @Query(
        """
            SELECT id, email, name 
            FROM users 
            WHERE email = :userEmail
        """, nativeQuery = true
    )
    fun userByEmail(
        @Param("userEmail") userEmail: String
    ): UserMainInfoDto?
}