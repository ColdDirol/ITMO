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
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    implementation("org.apache.logging.log4j:log4j-core:3.0.0-beta2")
    implementation("org.apache.logging.log4j:log4j-api:3.0.0-beta2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.jacoco:org.jacoco.agent:0.8.12")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        html.required.set(true)
    }
}