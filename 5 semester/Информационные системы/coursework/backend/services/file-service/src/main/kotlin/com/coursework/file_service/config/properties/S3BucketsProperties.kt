package com.coursework.file_service.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

typealias BucketKey = String
const val COURSE_FILES_BUCKET: BucketKey = "files-management"

@ConfigurationProperties(prefix = "s3")
class S3BucketsProperties(
    val buckets: Map<String, String>
) {
    operator fun get(key: String): String = buckets[key] ?: throw IllegalArgumentException("Invalid bucket key")
}