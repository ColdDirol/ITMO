package com.coursework.file_service.s3.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("s3.datasource")
class S3Properties(
    val url: String,
    val user: String,
    val password: String
)