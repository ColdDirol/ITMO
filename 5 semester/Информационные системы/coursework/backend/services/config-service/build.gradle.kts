import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.springframework.cloud:spring-cloud-config-server:4.1.3")
}

springBoot {
	buildInfo()
}

tasks.withType<BootJar> {
	archiveFileName.set("config-service.war")
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