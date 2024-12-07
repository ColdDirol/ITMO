package com.coursework.file_service.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "s3.config")
data class S3ConfigProperties(
    val baseUrl: String,
    val accessKey: String,
    val secretKey: String,
)