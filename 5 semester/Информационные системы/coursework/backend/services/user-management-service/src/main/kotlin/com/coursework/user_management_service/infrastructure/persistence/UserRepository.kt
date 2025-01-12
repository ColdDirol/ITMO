package com.coursework.user_management_service.infrastructure.persistence

import com.coursework.user_management_service.infrastructure.model.UsersEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<com.coursework.user_management_service.infrastructure.model.UsersEntity, Long> {

    fun findByEmail(email: String): Optional<com.coursework.user_management_service.infrastructure.model.UsersEntity>
    fun existsByEmail(email: String): Boolean
}