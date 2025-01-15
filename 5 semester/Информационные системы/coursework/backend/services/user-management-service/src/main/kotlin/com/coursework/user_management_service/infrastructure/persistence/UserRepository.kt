package com.coursework.user_management_service.infrastructure.persistence

import com.coursework.user_management_service.infrastructure.model.UsersEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UsersEntity, Long> {

    fun findByEmail(email: String): Optional<UsersEntity>
    fun existsByEmail(email: String): Boolean

    @Query("SELECT u FROM UsersEntity u WHERE " +
            "(LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    fun searchUsers(searchTerm: String, pageable: Pageable): Page<UsersEntity>
}