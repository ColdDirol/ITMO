package com.coursework.file_service.infrastructure.storage.repo

import com.coursework.file_service.infrastructure.storage.model.VideoPathEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VideoPathRepository: JpaRepository<VideoPathEntity, Long> {
}