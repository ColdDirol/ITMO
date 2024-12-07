package com.coursework.file_service.infrastructure.storage.model

import jakarta.persistence.*

@Entity
@Table(name = "path")
class PathEntity(
    @Id
    val id: Long,
    val bucket: String,
    val fileName: String,
)