package com.coursework.file_service.infrastructure.storage.model

import jakarta.persistence.*

@Entity
@Table(name = "video_path")
class VideoPathEntity(
    @Id
    var id: Long,
    var name: String,
    var description: String,
    @OneToOne
    var path: PathEntity,
)