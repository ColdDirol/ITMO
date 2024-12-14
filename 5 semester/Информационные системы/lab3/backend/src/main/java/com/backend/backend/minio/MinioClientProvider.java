package com.backend.backend.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
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
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании бакета: " + bucket, e);
        }
    }

    public MinioClient getClient() {
        return minioClient;
    }

    @PreDestroy
    private void cleanup() {
        // TODO
    }
}
