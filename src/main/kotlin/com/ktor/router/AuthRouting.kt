package com.ktor.router

import com.domain.models.UpdateUser
import com.domain.security.JwtConfig
import com.domain.usecase.user.ProviderUserCase
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.authRouting() {
    routing {
        post("/auth/login") {
            val request = call.receive<UpdateUser>()
            val token = ProviderUserCase.login(request.username, request.password)

            if (token != null) {
                call.respond(HttpStatusCode.OK, mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Usuario o contraseña incorrectos")
            }
        }

        post("/auth/register") {
            val request = call.receive<UpdateUser>()
            val newUser = ProviderUserCase.register(request)

            if (newUser != null) {
                val token = JwtConfig.generateToken(newUser.name!!)
                call.respond(HttpStatusCode.Created, mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Conflict, mapOf("error" to "El usuario ya existe"))
            }
        }

        post("/auth/logout") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal?.payload?.getClaim("username")?.asString()

            if (username != null) {
                val success = ProviderUserCase.logout(username)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Logout exitoso"))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al cerrar sesión"))
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Token no válido"))
            }
        }

    }
}
