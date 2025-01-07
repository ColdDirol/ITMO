package com.coursework.file_service.domain

import com.coursework.file_service.domain.video.SaveVideo
import com.coursework.file_service.domain.video.GetVideo
import org.springframework.stereotype.Service

@Service
class VideoService(
    val saveVideo: SaveVideo,
    val getVideo: GetVideo,
)