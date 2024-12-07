package internal

import com.newdex.services.contract.common.internal.PathDto

data class VideoPathInternalDto(
    val id: Long,
    val name: String,
    val description: String,
    val path: PathDto,
)