rootProject.name = "backend"

val lib = listOf(
    "security"
)

val libContracts = listOf(
    "common",
    "user-management",
    "account-management",
    "file-management",
)

val services = listOf(
    "config-service",
    "api-gateway-service",
    "file-service",
    "notification-service",
    "user-management-service",
    "account-management-service"
)

// Подключение всех контрактов из lib:contract
libContracts.forEach { contract ->
    include("lib:contract:$contract")
    project(":lib:contract:$contract")?.name = contract
}

// Подключение всех сервисов
services.forEach { service ->
    include("services:$service")
    project(":services:$service")?.name = service
}

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
//    val springCloudVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.allopen" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.noarg" -> useVersion(kotlinVersion)

                "org.springframework.boot" -> useVersion(springBootVersion)
//                "org.springframework.cloud" -> useVersion(springCloudVersion)
                "io.spring.dependency-management" -> useVersion("1.0.14.RELEASE")
            }
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
