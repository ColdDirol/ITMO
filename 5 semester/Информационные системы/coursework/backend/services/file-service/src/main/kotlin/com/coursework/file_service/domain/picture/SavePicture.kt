package com.coursework.file_service.domain.picture

import com.coursework.file_service.infrastructure.model.PicturePathEntity
import com.coursework.file_service.infrastructure.persistence.PicturePathRepository
import com.coursework.file_service.s3.S3Factory
import internal.PathDto
import io.minio.PutObjectArgs
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.*

@Service
class SavePicture(
    private val picturePathRepository: PicturePathRepository
) {
    operator fun invoke(picture: InputStream): PathDto {
        val minioClient = S3Factory.getInstance()

        val filePath = UUID.randomUUID().toString() + ".png"
        val size = picture.available().toLong()

        val putObjectArgs = PutObjectArgs.builder()
            .bucket(S3Factory.PICTURE_BUCKET)
            .`object`(filePath)
            .contentType("image/png")
            .stream(picture, size, -1)
            .build()

        minioClient.putObject(putObjectArgs).etag()

        val savedEntity = picturePathRepository.save(
            PicturePathEntity(
                id = 0,
                bucket = S3Factory.PICTURE_BUCKET,
                path = filePath
            )
        )

        return with(savedEntity) {
            PathDto(id, bucket, path)
        }
    }
}