import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

abstract class CompileJavaTask : DefaultTask() {
    @TaskAction
    fun compile() {
        println("Compiling Java code...")
        // Use Gradle's built-in compileJava task
        project.tasks.compileJava.get().execute()
    }
}

abstract class BuildJarTask : DefaultTask() {
    @TaskAction
    fun build() {
        println("Building JAR file...")
        // Use Gradle's built-in jar task
        project.tasks.jar.get().execute()
    }
}