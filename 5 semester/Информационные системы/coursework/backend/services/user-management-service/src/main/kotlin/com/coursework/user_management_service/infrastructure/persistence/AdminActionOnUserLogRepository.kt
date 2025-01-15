package com.coursework.user_management_service.infrastructure.persistence

import com.coursework.user_management_service.infrastructure.model.AdminActionOnUserLogEntity
import com.coursework.user_management_service.infrastructure.model.UsersEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminActionOnUserLogRepository : JpaRepository<AdminActionOnUserLogEntity, Long> {

    fun findByUser(user: UsersEntity): MutableList<AdminActionOnUserLogEntity>

    fun findByAdministrator(administrator: UsersEntity): List<AdminActionOnUserLogEntity>

    fun findAllByUser_Email(userEmail: String, pageable: Pageable): Page<AdminActionOnUserLogEntity>

//    fun findAll(pageable: Pageable): Page<AdminActionOnUserLogEntity>
}