package com.coursework.file_service.domain

import com.coursework.file_service.domain.video.CreateVideoPathAction
import com.coursework.file_service.domain.video.GetVideoPathAction
import org.springframework.stereotype.Service

@Service
class VideoService(
    val createVideoPath: CreateVideoPathAction,
    val getVideoPath: GetVideoPathAction,
)