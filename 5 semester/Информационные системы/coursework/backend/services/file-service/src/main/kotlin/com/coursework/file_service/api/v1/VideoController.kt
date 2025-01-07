package com.coursework.file_service.api.v1

import com.coursework.file_service.domain.VideoService
import internal.PathDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.InputStream

@RestController
@RequestMapping("/api/v1/video")
class VideoController(
    private val videoService: VideoService
) {

    @PostMapping
    fun saveVideo(@RequestBody video: InputStream): ResponseEntity<PathDto> {
        return ResponseEntity.ok(
            videoService.saveVideo(video)
        )
    }

    @GetMapping("/{id}")
    fun getPicture(@PathVariable id: Long): ResponseEntity<InputStream> {
        return ResponseEntity.ok(
            videoService.getVideo(id)
        )
    }
}