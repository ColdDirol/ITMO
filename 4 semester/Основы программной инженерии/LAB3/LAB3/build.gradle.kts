import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.DataLine

plugins {
    java
    war
}

repositories {
    mavenCentral()
}

dependencies {
    providedCompile("javax.servlet:servlet-api:2.5")
    implementation("jakarta.platform:jakarta.jakartaee-web-api:9.1.0")
    implementation("org.primefaces:primefaces:13.0.3:jakarta")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:4.0.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0-M1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.0-M1")
}

tasks.war {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    webAppDirectory = file("src/main/webapp")
    webXml = file("src/main/webapp/WEB-INF/web.xml") // copies a file to WEB-INF/web.xml
}

tasks.register<Test>("lab-test") {
    dependsOn("lab-build")
    useJUnitPlatform()
}

tasks.register<JavaCompile>("lab-compile") {
    source = sourceSets["main"].java
    classpath = sourceSets["main"].compileClasspath
    destinationDirectory.set(file("build/classes/java/main"))
    source = sourceSets["test"].java
    classpath = sourceSets["test"].compileClasspath
    destinationDirectory.set(file("build/classes/java/test"))
}

tasks.register<Jar>("lab-build") {
    dependsOn("lab-compile")
    from("build/classes/java/main")
    archiveBaseName.set("LAB3")
    archiveVersion.set("1.0.0")
}

tasks.register("lab-clean") {
    doLast {
        delete("build")
    }
}

tasks.register("lab-music") {
    dependsOn("lab-build")
    doLast {
        val file = File("anime-ahh.wav")
        val audioInputStream = AudioSystem.getAudioInputStream(file)
        val audioFormat = audioInputStream.format
        val info = DataLine.Info(Clip::class.java, audioFormat)
        val line = AudioSystem.getLine(info) as Clip
        line.open(audioInputStream)
        line.start()
        while (line.frameLength > line.framePosition) {
            Thread.sleep(100)
        }
        line.close()
    }
}