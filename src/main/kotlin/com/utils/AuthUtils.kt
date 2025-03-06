package com.utils

import com.domain.usecase.ProviderUserCase
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

suspend fun ApplicationCall.validateToken(): Boolean {
    val principal = this.principal<JWTPrincipal>()
    val username = principal?.payload?.getClaim("email")?.asString()
    val token = this.request.headers["Authorization"]?.removePrefix("Bearer ")

    if (username == null || token == null) {
        this.respond(HttpStatusCode.Unauthorized, "Token no válido")
        return false
    }

    val isTokenValid = ProviderUserCase.isTokenValid(username, token)
    if (!isTokenValid) {
        this.respond(HttpStatusCode.Unauthorized, "Token inválido o expirado")
        return false
    }
    return true
}
