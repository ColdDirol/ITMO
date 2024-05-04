plugins {
    id("java")
}

group = "com.colddirol"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("jakarta.platform:jakarta.jakartaee-web-api:11.0.0-M2")
    implementation("org.primefaces:primefaces:14.0.0")
    implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:4.0.2")
}

tasks.test {
    useJUnitPlatform()
}