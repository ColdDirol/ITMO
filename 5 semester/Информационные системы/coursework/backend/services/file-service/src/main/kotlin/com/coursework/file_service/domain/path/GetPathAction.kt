package com.coursework.file_service.domain.path

import com.newdex.services.contract.common.internal.PathDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class GetPathAction {
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    operator fun invoke(pathId: Long): PathDto {

    }
}