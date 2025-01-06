package com.coursework.file_service.domain.video

import com.newdex.services.contract.common.internal.PathDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class GetVideoPathAction {
    operator fun invoke(id: Long): PathDto {
        return PathDto(id, "aaa", "aaa")
    }
}