package com.coursework.file_service.domain.video

import com.coursework.file_service.infrastructure.persistence.VideoPathRepository
import com.coursework.file_service.s3.S3Factory
import io.minio.GetObjectArgs
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class GetVideo(
    private val videoPathRepository: VideoPathRepository
) {
    operator fun invoke(id: Long): InputStream {
        val minioClient = S3Factory.getInstance()

        val path = videoPathRepository.findById(id)
            .orElseThrow { IllegalArgumentException("File does not exist") }

        val objectStream = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(S3Factory.VIDEO_BUCKET)
                .`object`(path.path)
                .build()
        )

        return objectStream
    }
}