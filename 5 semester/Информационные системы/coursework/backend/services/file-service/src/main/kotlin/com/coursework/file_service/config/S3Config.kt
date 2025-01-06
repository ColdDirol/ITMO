//package com.coursework.file_service.config
//
//import io.minio.BucketExistsArgs
//import io.minio.MakeBucketArgs
//import io.minio.MinioClient
//import io.minio.errors.MinioException
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//class S3Config(
//    private val s3ConfigProperties: S3ConfigProperties,
//    private val s3BucketsProperties: S3BucketsProperties,
//) {
//    @Bean
//    fun s3Client(): MinioClient {
//        val minioClient = MinioClient
//            .Builder()
//            .endpoint(s3ConfigProperties.baseUrl)
//            .credentials(
//                s3ConfigProperties.accessKey,
//                s3ConfigProperties.secretKey
//            )
//            .build()
//
//        s3BucketsProperties.buckets.values.forEach {
//            ensureBucketExists(
//                minioClient,
//                it,
//            )
//        }
//
//        return minioClient
//    }
//
//    private fun ensureBucketExists(minioClient: MinioClient, bucketName: String) {
//        if (!minioClient.bucketExists(
//                BucketExistsArgs.Builder()
//                    .bucket(bucketName)
//                    .build()
//            )) {
//            try {
//                minioClient.makeBucket(
//                    MakeBucketArgs.Builder()
//                        .bucket(bucketName)
//                        .build()
//                )
//            } catch (e: MinioException) {
//                log.error("A mandatory bucket: $bucketName does not exist and the bucket can't be created because of ${e::class.java}", e)
//                throw InternalServerException("S3 storage configuration exception")
//            }
//        }
//    }
//}