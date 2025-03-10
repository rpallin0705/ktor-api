package com.ktor.router

import com.domain.models.Restaurant
import com.domain.models.User
import com.domain.models.UpdateUser
import com.domain.usecase.ProviderUserCase
import com.domain.usecase.ProviderUserCase.logger
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRouting() {
    routing {

        get("/") {
            call.respondText("Hello World!")
        }


        get("/user") {

            val userName = call.request.queryParameters["email"]
            logger.warn("El Name tiene de valor $userName")
            if (userName != null) {
                val user = ProviderUserCase.getUserByEmail(userName)
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound, "usuario no encontrado")
                } else {
                    call.respond(user)
                }
                return@get
            }
        }

        get("/user/{userName}") {
            val userName = call.parameters["userName"]
            if (userName == null) {
                call.respond(HttpStatusCode.BadRequest, "Debes pasar el email a buscar")
                return@get
            }

            val user = ProviderUserCase.getUserByEmail(userName)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "usuario no encontrado")
                return@get
            }
            call.respond(user)
        }

        post("/user") {
            try {
                val emp = call.receive<User>()
                val res = ProviderUserCase.insertUser(emp)
                if (!res) {
                    call.respond(HttpStatusCode.Conflict, "El usuario no pudo insertarse. Puede que ya exista")
                    return@post
                }
                call.respond(HttpStatusCode.Created, "Se ha insertado correctamente con nombre =  ${emp.email}")
            } catch (e: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de datos o lectura del cuerpo.")
            } catch (e: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest, " Problemas en la conversión json")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Error en los datos. Probablemente falten.")
            }
        }

        patch("/user/{userName}") {
            try {
                val name = call.parameters["userName"]
                name?.let {
                    val updateUser = call.receive<UpdateUser>()
                    val res = ProviderUserCase.updateUser(updateUser, name)
                    if (!res) {
                        call.respond(HttpStatusCode.Conflict, "El usuario no pudo modificarse. Puede que no exista")
                        return@patch
                    }
                    call.respond(HttpStatusCode.Created, "Se ha actualizado correctamente con email =  ${name}")
                } ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Debes identificar el usuario")
                    return@patch
                }
            } catch (e: IllegalStateException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Error en el formado de envío de los datos o lectura del cuerpo."
                )
            } catch (e: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest, "Error en el formado de json")
            }
        }


        delete("/user/{userName}") {
            val name = call.parameters["userName"]
            logger.warn("Queremos borrar el usuario con email $name")
            name?.let {
                val res = ProviderUserCase.deleteUser(name)
                if (!res) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        "usuario no encontrado para borrar"
                    )
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            } ?: run {
                call.respond(HttpStatusCode.NoContent, "Debes identintificar el usuario")
            }
            return@delete
        }

        staticResources("/static", "static")
    }
}


