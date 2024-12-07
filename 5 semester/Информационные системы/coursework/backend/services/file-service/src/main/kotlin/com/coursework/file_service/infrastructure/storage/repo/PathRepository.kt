package com.coursework.file_service.infrastructure.storage.repo

import com.coursework.file_service.infrastructure.storage.model.PathEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PathRepository: JpaRepository<PathEntity, Long> {
}