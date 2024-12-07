rootProject.name = "backend"

include("lib:contract:common")
include("lib:contract:user-management")
include("lib:contract:administration-management")
include("lib:contract:account-management")

include("services:config-service")
include("services:api-gateway-service")
include("services:file-service")
include("services:notification-service")

include("services:user-management-service")
include("services:account-management-service")

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.allopen" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.noarg" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)

                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion("1.1.6")
            }
        }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
include("lib:contract:file-management")
findProject(":lib:contract:file-management")?.name = "file-management"
include("lib:contract:file-management")
findProject(":lib:contract:file-management")?.name = "file-management"
include("lib:contract:services-common")
findProject(":lib:contract:services-common")?.name = "services-common"
include("lib:services-common")
findProject(":lib:services-common")?.name = "services-common"
include("services:user-management-service")
findProject(":services:user-management-service")?.name = "user-management-service"
