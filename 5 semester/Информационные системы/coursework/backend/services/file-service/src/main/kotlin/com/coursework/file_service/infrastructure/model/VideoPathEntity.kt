package com.coursework.file_service.infrastructure.model

import jakarta.persistence.*

@Entity
@Table(name = "video_path")
class VideoPathEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    val bucket: String,
    var path: String,
)