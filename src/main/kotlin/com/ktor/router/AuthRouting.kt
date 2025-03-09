package com.ktor.router

import com.domain.models.UpdateUser
import com.domain.usecase.ProviderUserCase
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
            val token = ProviderUserCase.login(request.email, request.password)

            if (token != null) {
                call.respond(HttpStatusCode.OK, mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Usuario o contraseña incorrectos")
            }
        }

        post("/auth/register") {
            val request = call.receive<UpdateUser>()
            val isRegistered = ProviderUserCase.register(request)

            if (isRegistered) {
                call.respond(HttpStatusCode.Created, "Usuario registrado correctamente")
            } else {
                call.respond(HttpStatusCode.Conflict, "Usuario ya existe")
            }
        }

        authenticate("jwt-auth") {
            post("/auth/logout") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal?.payload?.getClaim("email")?.asString()

                if (username.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Token no válido"))
                    return@post
                }

                val success = ProviderUserCase.logout(username)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Logout exitoso"))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Error al cerrar sesión"))
                }
            }
        }

        authenticate("jwt-auth") {
            get("/auth/validate-token") {
                val principal = call.principal<JWTPrincipal>()
                val email = principal?.payload?.getClaim("email")?.asString()
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")

                if (email.isNullOrBlank() || token.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Token inválido")
                    return@get
                }

                val isValid = ProviderUserCase.validateToken(email, token)
                if (isValid) {
                    call.respond(HttpStatusCode.OK, mapOf("valid" to true))
                } else {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        mapOf("valid" to false, "message" to "Token expirado o inválido")
                    )
                }
            }
        }
    }
}
