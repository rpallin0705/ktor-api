package com.ktor

import com.ktor.router.authRouting
import com.ktor.router.restaurantRouting
import com.ktor.router.userRouting
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.myModule() {
    configureSecurity()
    configureSerialization()
    configureDatabases()

    routing {
        authRouting()

        authenticate("jwt-auth") {
            userRouting()
            restaurantRouting()
        }
    }
}
