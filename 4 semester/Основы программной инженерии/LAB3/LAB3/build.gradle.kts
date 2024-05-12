plugins {
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
    testImplementation("junit:junit:3.8.1")
}

tasks.war {
    webAppDirectory = file("src/main/webapp")
    webXml = file("src/src/main/webapp/WEB-INF/web.xml") // copies a file to WEB-INF/web.xml
}
