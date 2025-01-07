package com.coursework.file_service.api.v1

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/public")
class PublicController {

    @PostMapping
    fun public(): String {
        return "Hello"
    }
}