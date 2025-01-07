package com.coursework.file_service.s3.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("s3.buckets")
class BucketProperties(
    val pictureBucket: String,
    val videoBucket: String
)