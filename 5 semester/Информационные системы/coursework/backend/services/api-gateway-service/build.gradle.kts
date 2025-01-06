import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	kotlin("jvm")
	kotlin("plugin.jpa")
	kotlin("plugin.allopen")
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

allOpen {
	annotations(
		"jakarta.persistence.MappedSuperclass",
		"jakarta.persistence.Entity",
		"org.springframework.context.annotation.Configuration",
	)
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-gateway:4.2.0")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation(project(":lib:contract:common"))
}

springBoot {
	buildInfo()
}

tasks.withType<BootJar> {
	archiveFileName.set("api-gateway-service.war")
}

tasks.withType<Test> {
	useJUnitPlatform()
	this.environment["SPRING_PROFILES_ACTIVE"] = "test"
}

tasks.getByName<Jar>("jar") {
	enabled = false
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}
