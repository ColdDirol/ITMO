package internal

data class VideoPathInternalDto(
    val id: Long,
    val name: String,
    val description: String,
    val path: PathDto,
)