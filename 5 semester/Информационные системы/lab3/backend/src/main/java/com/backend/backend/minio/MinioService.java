package com.backend.backend.minio;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.InputStream;

@ApplicationScoped
public class MinioService {

    @Inject
    private MinioClientProvider clientProvider;

    private String bucketName = "imports";

    public void uploadFile(String objectName, InputStream inputStream, long size, String contentType) throws Exception {
        MinioClient client = clientProvider.getClient();
        client.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, size, -1)
                .contentType(contentType)
                .build());
    }

    public InputStream getFile(String objectName) throws Exception {
        MinioClient client = clientProvider.getClient();
        return client.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }

    public void deleteFile(String objectName) throws Exception {
        MinioClient client = clientProvider.getClient();
        client.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }
}