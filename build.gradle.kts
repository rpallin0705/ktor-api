
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    applicationName = "rapl-proyectos-integrado"
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()

}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
  //  implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
    implementation("mysql:mysql-connector-java:8.0.33")


    implementation(libs.exposed.dao)
    implementation(libs.mariadb)  // Usando la versión desde el archivo libs.versions.toml
    implementation(libs.exposedjdbc)  // Usando la versión desde el archivo libs.versions.toml
   // implementation("org.mariadb.jdbc:mariadb-java-client:2.7.3")



    // MariaDB Driver
   // implementation("org.mariadb.jdbc:mariadb-java-client:2.7.3")
   // implementation("org.jetbrains.exposed:exposed-jdbc:0.56.0")

}
