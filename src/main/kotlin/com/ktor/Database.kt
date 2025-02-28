package com.ktor

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val config = environment.config.config("database")

    val driver = config.property("driver").getString()
    val url = config.property("url").getString()
    val username = config.property("username").getString()
    val password = config.property("password").getString()

    try {
        Database.connect(
            url = url,
            driver = driver,
            user = username,
            password = password
        )
        log.info("Conexión establecida correctamente con la base de datos")
    } catch (e: Exception) {
        log.error("Error al conectar a la base de datos: ${e.message}", e)
    }
}
