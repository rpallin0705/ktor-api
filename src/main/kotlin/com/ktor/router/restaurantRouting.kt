package com.ktor.router

import com.domain.models.Restaurant
import com.domain.usecase.ProviderRestaurantCase
import com.domain.usecase.ProviderUserCase
import com.utils.validateToken
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.restaurantRouting() {
    routing {
        authenticate("jwt-auth") {

            get("/restaurant") {
                if (!call.validateToken()) return@get
                val restaurants = ProviderRestaurantCase.getAllRestaurants()
                call.respond(restaurants)
            }

            get("/restaurant/{id}") {
                if (!call.validateToken()) return@get
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@get
                }

                val restaurant = ProviderRestaurantCase.getRestaurantsById(id)
                if (restaurant == null) {
                    call.respond(HttpStatusCode.NotFound, "Restaurante no encontrado")
                } else {
                    call.respond(restaurant)
                }
            }

            post("/restaurant") {
                if (!call.validateToken()) return@post
                try {
                    val restaurant = call.receive<Restaurant>()
                    val insertedRestaurant = ProviderRestaurantCase.insertRestaurant(restaurant)
                    if (insertedRestaurant == null) {
                        call.respond(HttpStatusCode.Conflict, "No se pudo insertar el restaurante")
                    } else {
                        call.respond(HttpStatusCode.Created, insertedRestaurant)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error en la solicitud: ${e.message}")
                }
            }

            patch("/restaurant/{id}") {
                if (!call.validateToken()) return@patch
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@patch
                }

                try {
                    val updatedRestaurant = call.receive<Restaurant>()
                    val restaurantUpdated = ProviderRestaurantCase.updateRestaurant(id, updatedRestaurant)
                    if (restaurantUpdated == null) {
                        call.respond(HttpStatusCode.Conflict, "No se pudo actualizar el restaurante")
                    } else {
                        call.respond(HttpStatusCode.OK, restaurantUpdated) // Devuelve el JSON del restaurante actualizado
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error en la solicitud: ${e.message}")
                }
            }

            delete("/restaurant/{id}") {
                if (!call.validateToken()) return@delete
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@delete
                }

                val isDeleted = ProviderRestaurantCase.deleteRestaurant(id)
                if (!isDeleted) {
                    call.respond(HttpStatusCode.NotFound, "Restaurante no encontrado")
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }
        }
    }
}