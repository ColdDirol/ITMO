package com.newdex.services.contract.common.internal

import java.time.LocalDateTime
import java.util.*

data class ModificationInfoNoUserInfoDto(
    val creationDate: LocalDateTime,
    val lastModificationDate: LocalDateTime,
    val lastModificationAuthor: String,
)

data class LessonVersionBindingDto(
    val id: Long,
    val lessonId: Long,
    val lessonVersionId: Long,
)

data class ModuleGradingStructureDto(
    val moduleId: Long,
    val blockUsageUuids: List<UUID>
)
