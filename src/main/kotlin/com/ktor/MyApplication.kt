package com.ktor

import com.ktor.router.restaurantRouting
import com.ktor.router.userRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.myModule() {
    configureSerialization()
    configureDatabases()
    userRouting()
    restaurantRouting()

}
