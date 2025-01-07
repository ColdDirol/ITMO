package com.coursework.file_service.domain

import com.coursework.file_service.domain.picture.SavePicture
import com.coursework.file_service.domain.picture.GetPicture
import org.springframework.stereotype.Service

@Service
class PictureService(
    val savePicture: SavePicture,
    val getPicture: GetPicture,
)