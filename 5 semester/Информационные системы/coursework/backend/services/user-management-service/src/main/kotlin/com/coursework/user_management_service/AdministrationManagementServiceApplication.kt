package com.coursework.user_management_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class AdministrationManagementServiceApplication

fun main(args: Array<String>) {
	runApplication<AdministrationManagementServiceApplication>(*args)
}
