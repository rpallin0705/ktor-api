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
            // Obtener todos los restaurantes
            get("/restaurant") {
                if (!call.validateToken()) return@get
                val restaurants = ProviderRestaurantCase.getAllRestaurants()
                call.respond(restaurants)
            }

            // Obtener un restaurante por ID
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

            // Agregar un nuevo restaurante
            post("/restaurant") {
                if (!call.validateToken()) return@post
                try {
                    val restaurant = call.receive<Restaurant>()
                    val isInserted = ProviderRestaurantCase.insertRestaurant(restaurant)
                    if (!isInserted) {
                        call.respond(HttpStatusCode.Conflict, "No se pudo insertar el restaurante")
                    } else {
                        call.respond(HttpStatusCode.Created, "Restaurante agregado correctamente")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error en la solicitud: ${e.message}")
                }
            }

            // Actualizar un restaurante por ID
            patch("/restaurant/{id}") {
                if (!call.validateToken()) return@patch
                val id = call.parameters["id"]?.toLongOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@patch
                }

                try {
                    val updatedRestaurant = call.receive<Restaurant>()
                    val isUpdated = ProviderRestaurantCase.updateRestaurant(id, updatedRestaurant)
                    if (!isUpdated) {
                        call.respond(HttpStatusCode.Conflict, "No se pudo actualizar el restaurante")
                    } else {
                        call.respond(HttpStatusCode.OK, "Restaurante actualizado correctamente")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error en la solicitud")
                }
            }

            // Eliminar un restaurante por ID
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