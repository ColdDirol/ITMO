package com.coursework.file_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [
	UserDetailsServiceAutoConfiguration::class
])
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.coursework.file_service.config.properties")
class FileServiceApplication

fun main(args: Array<String>) {
	runApplication<FileServiceApplication>(*args)
}
