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
include("services:administration-management-service")
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