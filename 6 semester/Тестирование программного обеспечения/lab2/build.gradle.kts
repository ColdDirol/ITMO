plugins {
    id("java")
}

group = "com.vladimir"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // program scope
    implementation("org.apache.commons:commons-csv:1.14.0")

    // test scope
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.16.1")

    implementation("org.jfree:jfreechart:1.5.5")
}

tasks.test {
    useJUnitPlatform()
}