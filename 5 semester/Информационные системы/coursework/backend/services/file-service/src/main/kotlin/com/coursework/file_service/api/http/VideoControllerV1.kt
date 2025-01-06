package com.coursework.file_service.api.http

import com.coursework.file_service.domain.VideoService
import com.newdex.services.contract.common.internal.PathDto
import org.springframework.core.io.support.ResourceRegion
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/video")
class VideoControllerV1(
    private val videoService: VideoService
) {

//    @GetMapping("/stream/{videoId}")
//    fun getVideoStream(
//        @PathVariable videoId: String,
//        @RequestHeader(value = HttpHeaders.RANGE, required = false) rangeHeader: String?
//    ): ResponseEntity<ResourceRegion> {
//        val videoPath: PathDto = Paths.get("videos/video_$videoId.mp4") // Путь к файлу видео
//        val video: Resource = UrlResource(videoPath.toUri())
//
//        val resourceRegion: ResourceRegion = if (rangeHeader != null) {
//            // Обработка диапазонов данных
//            buildResourceRegion(video, rangeHeader)
//        } else {
//            // Весь файл
//            ResourceRegion(video, 0, video.contentLength())
//        }
//
//        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//            .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
//            .body(resourceRegion)
//    }
//
//    private fun buildResourceRegion(video: Resource, rangeHeader: String): ResourceRegion {
//        val range = rangeHeader.replace("bytes=", "").split("-")
//        val videoSize = video.contentLength()
//
//        val start = range[0].toLong()
//        val end = if (range.size > 1 && range[1].isNotEmpty()) {
//            range[1].toLong()
//        } else {
//            videoSize - 1
//        }
//
//        val rangeLength = end - start + 1
//        return ResourceRegion(video, start, rangeLength.coerceAtMost(1024 * 1024)) // 1MB на фрагмент
//    }
}