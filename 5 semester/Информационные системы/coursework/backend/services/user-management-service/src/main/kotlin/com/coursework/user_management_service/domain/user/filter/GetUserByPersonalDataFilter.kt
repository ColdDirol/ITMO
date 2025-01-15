package com.coursework.user_management_service.domain.user.filter

import com.coursework.user_management_service.infrastructure.persistence.UserRepository
import external.UserInfoDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import user.external.UserSearchRequestDto

@Service
class GetUserByPersonalDataFilter(
    private val repository: UserRepository
) {
    operator fun invoke(dto: UserSearchRequestDto): Page<UserInfoDto> {
        val usersBySearchTerm = repository.searchUsers(
            dto.searchTerm, PageRequest.of(dto.page, dto.size)
        )

        return PageImpl(
            usersBySearchTerm.content.map { userEntity ->
                UserInfoDto(
                    id = userEntity.id,
                    email = userEntity.email,
                    role = userEntity.role,
                    phone = userEntity.phone,
                    name = userEntity.name,
                    sex = userEntity.sex.name,
                    dateOfBirth = userEntity.dateOfBirth,
                    status = userEntity.status
                )
            }, usersBySearchTerm.pageable, usersBySearchTerm.totalElements
        )
    }
}