package com.coursework.user_management_service.domain

import com.coursework.user_management_service.domain.admin.filter.GetUserCurrenciesFilter
import org.springframework.stereotype.Service

@Service
class AdminService(
    val getUserCurrencies: GetUserCurrenciesFilter,
)