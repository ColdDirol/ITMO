package com.backend.backend.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MinioClientProvider {

    private String url = "http://localhost:9000";
    private String accessKey = "colddirol";
    private String secretKey = "colddirol";
    private String bucket = "imports";

    private MinioClient minioClient;

    @PostConstruct
    private void init() {
        minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                minioClient.makeBucket(
                        MakeBucketArgs
                                .builder()
                                .bucket(bucket)
                                .build()
                );

//                minioClient.setBucketPolicy(
//                        SetBucketPolicyArgs.builder().bucket(bucket).config(generateBucketPolicy()).build()
//                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании бакета: " + bucket, e);
        }
    }

//    private String generateBucketPolicy() {
//        return "{\n" +
//                "    \"Version\": \"2012-10-17\",\n" +
//                "    \"Statement\": [\n" +
//                "        {\n" +
//                "            \"Effect\": \"Allow\",\n" +
//                "            \"Principal\": \"*\",\n" +
//                "            \"Action\": [\n" +
//                "                \"s3:GetObject\",\n" +
//                "                \"s3:PutObject\"\n" +
//                "            ],\n" +
//                "            \"Resource\": \"arn:aws:s3:::" + bucket + "/*\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//    }

    public MinioClient getClient() {
        return minioClient;
    }

    @PreDestroy
    private void destroy() {
        // TODO
    }
}
