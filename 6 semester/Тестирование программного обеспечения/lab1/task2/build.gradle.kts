group = "vladimir"
version = "1.0-SNAPSHOT"

plugins {
    id("java")
    id("jacoco")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.jacoco:org.jacoco.agent:0.8.12")

}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true) // Генерация отчета в формате XML
        csv.required.set(false) // Отключение отчета в формате CSV
        html.required.set(true) // Генерация отчета в формате HTML
    }
}