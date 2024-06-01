import java.io.ByteArrayOutputStream
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

// dependencies
val servletApiVersion: String by properties
val jakartaWebApiVersion: String by properties
val primefacesVersion: String by properties
val postgresqlVersion: String by properties
val eclipsePersistenceJpaVersion: String by properties
val junitJupiterApiVersion: String by properties
val junitJupiterEngineVersion: String by properties

// project paths
val webAppPath: String by properties
val webXmlPath: String by properties
val classesMainPath: String by properties
val classesTestPath: String by properties
val mainResourcesPath: String by properties
val mainWebappPath: String by properties

// jar
val jarName: String by properties
val jarVersion: String by properties

// trunk working copy path
val trunkPath: String by properties

dependencies {
    providedCompile("javax.servlet:servlet-api:${servletApiVersion}")
    implementation("jakarta.platform:jakarta.jakartaee-web-api:${jakartaWebApiVersion}")
    implementation("org.primefaces:primefaces:${primefacesVersion}")
    implementation("org.postgresql:postgresql:${postgresqlVersion}")
    implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:${eclipsePersistenceJpaVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitJupiterApiVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${junitJupiterEngineVersion}")
    testImplementation("org.jboss.logmanager:jboss-logmanager:2.1.19.Final")
}

tasks.war {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    webAppDirectory = file(webAppPath)
    webXml = file(webXmlPath) // copies a file to WEB-INF/web.xml
}

tasks.register<Test>("lab-test") {
    dependsOn("lab-build")
    useJUnitPlatform()
}

tasks.register<JavaCompile>("lab-compile") {
    source = sourceSets["main"].java
    classpath = sourceSets["main"].compileClasspath
    destinationDirectory.set(file(classesMainPath))
    source = sourceSets["test"].java
    classpath = sourceSets["test"].compileClasspath
    destinationDirectory.set(file(classesTestPath))
}

tasks.register<Jar>("lab-build") {
    dependsOn("lab-compile")
    from(classesMainPath)
    from(mainResourcesPath)
    from(mainWebappPath)
    archiveBaseName.set(jarName)
    archiveVersion.set(jarVersion)
    manifest {
        attributes["Main-Class"] = "Main"
    }

    doLast {
        project.exec {
            commandLine = "echo 'Project have been build'".split(" ")
        }
    }
}

tasks.register("lab-clean") {
    doLast {
        delete("build")
        delete(".gradle")
        delete(".database")
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


tasks.register("lab-diff") {
    doLast {
        val immutableClasses = File("immutable_classes.txt").readLines()
        println("immutableClasses: $immutableClasses")
        val byteOut = ByteArrayOutputStream()
        project.exec {
            commandLine = "svn status".split(" ")
            standardOutput = byteOut
        }
        val output = byteOut.toByteArray().toString(Charsets.UTF_8)
        val changedFiles = output.lines().filter { it.startsWith("M ") || it.startsWith("! ") }
            .map { it.trim().split("\\s+".toRegex(), 2)[1] }
        println("changedFiles: $changedFiles")
        if (immutableClasses.any { immutableFile -> changedFiles.any { changedFile -> changedFile.contains(immutableFile) } }) {
            println("Changes found in classes that should not be changed")
        } else {
            val projectDir = file(trunkPath)
            println("Current directory: ${projectDir.absolutePath}")

            project.exec {
                workingDir = projectDir
                commandLine = "sh svn.sh".split(" ")
            }
        }
    }
}