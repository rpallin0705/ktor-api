package com.ktor.router

import com.domain.usecase.ProviderUserCase
import com.domain.usecase.ProviderUserFavoritesCase
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.utils.validateToken

fun Route.userFavoritesRouting() {
    route("/favorites") {
        get {
            if (!call.validateToken()) return@get

            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid email")

            val user = ProviderUserCase.getUserByEmail(email)  // Obtener usuario desde la BD
            val userId = user?.id ?: return@get call.respond(HttpStatusCode.NotFound, "User not found")

            val favorites = ProviderUserFavoritesCase.getFavoriteRestaurants(userId)
            call.respond(favorites)
        }

        post("/{restaurantId}") {
            if (!call.validateToken()) return@post

            val principal = call.principal<JWTPrincipal>()
            val email = principal?.payload?.getClaim("email")?.asString()
                ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid email")

            val user = ProviderUserCase.getUserByEmail(email)  // Obtener usuario desde la BD
            val userId = user?.id ?: return@post call.respond(HttpStatusCode.NotFound, "User not found")

            val restaurantId = call.parameters["restaurantId"]?.toLongOrNull()
                ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid restaurant ID")

            val result = ProviderUserFavoritesCase.toggleFavorites(userId, restaurantId)
            if (result != null) {
                call.respond(result)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Failed to toggle favorite")
            }
        }
    }
}