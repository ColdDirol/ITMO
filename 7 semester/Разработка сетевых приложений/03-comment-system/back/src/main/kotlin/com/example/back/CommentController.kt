package com.example.back

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin
@RestController
class CommentController {

    // makeshift storage
    private val comments: MutableList<CommentModel> = mutableListOf()

    @GetMapping("/allComments")
    fun allComments(): MutableList<CommentModel> {
        return comments
    }

    @PostMapping("/addComment")
    fun addComment(@RequestBody comment: CommentDto): ResponseEntity<Any> {
        if (!isValidComment(comment)) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        val newComment = CommentModel(
            id = UUID.randomUUID(),
            author = comment.author,
            comment = comment.comment
        )

        comments.add(newComment)

        return ResponseEntity(HttpStatus.OK)
    }

    private fun isValidComment(comment: CommentDto): Boolean {
        if (comment.author.isBlank()) {
            return false
        }

        if (comment.comment.isBlank()) {
            return false
        }

        if (comment.author.length > 100) {
            return false
        }

        if (comment.comment.length > 1000) {
            return false
        }

        return true
    }
}