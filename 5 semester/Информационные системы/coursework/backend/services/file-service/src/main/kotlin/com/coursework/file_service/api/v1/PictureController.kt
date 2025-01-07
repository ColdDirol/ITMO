package com.coursework.file_service.api.v1

import com.coursework.file_service.domain.PictureService
import internal.PathDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.InputStream

@RestController
@RequestMapping("/api/v1/picture")
class PictureController(
    private val pictureService: PictureService
) {

    @PostMapping
    fun savePicture(@RequestBody picture: InputStream): ResponseEntity<PathDto> {
        return ResponseEntity.ok(
            pictureService.savePicture(picture)
        )
    }

    @GetMapping("/{id}")
    fun getPicture(@PathVariable id: Long): ResponseEntity<InputStream> {
        return ResponseEntity.ok(
            pictureService.getPicture(id)
        )
    }
}