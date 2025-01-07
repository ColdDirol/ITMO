package com.coursework.file_service.s3

import com.coursework.file_service.s3.properties.BucketProperties
import com.coursework.file_service.s3.properties.S3Properties
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient

object S3Factory {
    private const val URL = "http://localhost:9000"
    private const val USER = "colddirol"
    private const val PASSWORD = "colddirol"
    const val PICTURE_BUCKET = "picture"
    const val VIDEO_BUCKET = "video"

    private val minioClient: MinioClient by lazy {
        val client = MinioClient.builder()
            .endpoint(URL)
            .credentials(USER, PASSWORD)
            .build()

        if (!client.bucketExists(BucketExistsArgs.builder().bucket(PICTURE_BUCKET).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(PICTURE_BUCKET).build())
        }

        if (!client.bucketExists(BucketExistsArgs.builder().bucket(VIDEO_BUCKET).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(VIDEO_BUCKET).build())
        }

        client
    }

    fun getInstance() = minioClient
}
