package com.coursework.file_service.domain.video

import com.coursework.file_service.infrastructure.model.VideoPathEntity
import com.coursework.file_service.infrastructure.persistence.VideoPathRepository
import com.coursework.file_service.s3.S3Factory
import internal.PathDto
import io.minio.PutObjectArgs
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.*

@Service
class SaveVideo(
    private val videoPathRepository: VideoPathRepository
) {
    operator fun invoke(video: InputStream): PathDto {
        val minioClient = S3Factory.getInstance()

        val filePath = UUID.randomUUID().toString()
        val size = video.available().toLong()

        val putObjectArgs = PutObjectArgs.builder()
            .bucket(S3Factory.VIDEO_BUCKET)
            .`object`(filePath)
            .contentType("video/mp4")
            .stream(video, size, -1)
            .build()

        minioClient.putObject(putObjectArgs).etag()

        val savedEntity = videoPathRepository.save(
            VideoPathEntity(
                id = 0,
                bucket = S3Factory.VIDEO_BUCKET,
                path = filePath
            )
        )

        return with(savedEntity) {
            PathDto(id, bucket, path)
        }
    }
}