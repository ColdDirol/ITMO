package com.coursework.user_management_service.infrastructure.repo

import com.coursework.user_management_service.infrastructure.model.UsersEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UsersEntity, Long> {

}