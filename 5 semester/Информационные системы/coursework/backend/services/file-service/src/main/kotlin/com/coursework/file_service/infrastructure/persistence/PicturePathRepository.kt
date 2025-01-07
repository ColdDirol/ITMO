package com.coursework.file_service.infrastructure.persistence

import com.coursework.file_service.infrastructure.model.PicturePathEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PicturePathRepository: JpaRepository<PicturePathEntity, Long>