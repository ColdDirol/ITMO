package com.coursework.file_service.domain.picture

import com.coursework.file_service.infrastructure.persistence.PicturePathRepository
import com.coursework.file_service.s3.S3Factory
import io.minio.GetObjectArgs
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class GetPicture(
    private val picturePathRepository: PicturePathRepository
) {
    operator fun invoke(id: Long): InputStream {
        val minioClient = S3Factory.getInstance()

        val path = picturePathRepository.findById(id)
            .orElseThrow { IllegalArgumentException("File does not exist") }

        val objectStream = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(S3Factory.PICTURE_BUCKET)
                .`object`(path.path)
                .build()
        )

        return objectStream
    }
}