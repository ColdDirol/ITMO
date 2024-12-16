package com.backend.backend.minio;

import io.minio.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.util.UUID;

@ApplicationScoped
public class MinioService {

    @Inject
    private MinioClientProvider clientProvider;

    private String bucketName = "imports";

    @Deprecated
    public void uploadFile(String objectName, InputStream inputStream, long size, String contentType) throws Exception {
//        clientProvider.getClient().putObject(
//                PutObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(objectName)
//                    .stream(inputStream, size, -1)
//                    .contentType(contentType)
//                    .build()
//        );
    }

    public InputStream getFile(String objectName) throws Exception {
        return clientProvider.getClient()
                .getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }

    public void deleteFile(String objectName) throws Exception {
        clientProvider.getClient()
                .removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }

    public boolean uploadUncommitedFile(UUID id, InputStream stream, long size) {
        try {
            clientProvider.getClient().putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(id + "_lock.csv")
                            .contentType("text/csv")
                            .stream(stream, size, -1)
                            .build()
            );

            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public void commitFile(UUID fileId) throws Exception {
        MinioClient client = clientProvider.getClient();
        client.copyObject(
                CopyObjectArgs.builder()
                        .source(CopySource.builder()
                                .bucket(bucketName)
                                .object(fileId + "_lock.csv")
                                .build())
                        .bucket(bucketName)
                        .object(fileId + ".csv")
                        .build()
        );
        client.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileId + "_lock.csv")
                        .build()
        );
    }

    public void rollbackFile(UUID id) throws Exception {
        clientProvider.getClient().removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(id + "_lock.csv")
                        .build()
        );
    }

}