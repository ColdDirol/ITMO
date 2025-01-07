package com.coursework.api_gateway_service

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