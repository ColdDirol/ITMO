package com.example.back

import java.util.*

/**
 * Mustn't be change or removed
 */
data class CommentModel (
        val id: UUID,
        val author: String,
        val comment: String
)
