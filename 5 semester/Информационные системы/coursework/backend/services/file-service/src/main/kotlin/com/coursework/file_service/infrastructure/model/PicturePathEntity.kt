package com.coursework.file_service.infrastructure.model

import jakarta.persistence.*

@Entity
@Table(name = "picture_path")
class PicturePathEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    val bucket: String,
    var path: String,
)