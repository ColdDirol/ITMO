package com.coursework.user_management_service.domain.admin.filter

import com.coursework.user_management_service.infrastructure.persistence.AdminRepository
import org.springframework.stereotype.Service

@Service
class GetUserCurrenciesFilter(
    private val adminRepository: AdminRepository,
) {
    operator fun invoke(userId: Long): List<String> = adminRepository
        .getUserCurrencyNames(userId)
}