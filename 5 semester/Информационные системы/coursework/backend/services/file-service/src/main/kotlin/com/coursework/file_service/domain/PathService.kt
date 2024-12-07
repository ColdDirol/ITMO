package com.coursework.file_service.domain

import com.coursework.file_service.domain.path.CreatePathAction
import com.coursework.file_service.domain.path.GetPathAction
import org.springframework.stereotype.Service

@Service
class PathService(
    val createPath: CreatePathAction,
    val getPath: GetPathAction,
)