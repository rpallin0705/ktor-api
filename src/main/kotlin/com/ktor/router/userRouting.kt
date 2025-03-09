package com.ktor.router

import com.domain.models.User
import com.domain.models.UpdateUser
import com.domain.usecase.ProviderUserCase
import com.domain.usecase.ProviderUserCase.logger
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

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

        post("/user/{email}/uploadImage") {
            val email = call.parameters["email"]
            if (email == null) {
                call.respond(HttpStatusCode.BadRequest, "Falta el parámetro email")
                return@post
            }

            val userDir = File("uploads/$email")
            if (!userDir.exists()) {
                userDir.mkdirs()
            }

            val multipartData = call.receiveMultipart()
            var fileName: String? = null

            multipartData.forEachPart { part ->
                if (part is PartData.FileItem) {
                    val extension = part.originalFileName?.substringAfterLast(".", "png") ?: "png"

                    fileName = "$userDir/${email}_UploadImage.$extension"

                    val file = File(fileName!!)

                    val inputStream = part.streamProvider()
                    val outputFile = file.outputStream().buffered()

                    inputStream.use { input -> outputFile.use { output -> input.copyTo(output) } }
                }
                part.dispose()
            }

            if (fileName != null) {
                val success = ProviderUserCase.uploadUserProfilePicture(email, fileName!!)
                if (success) {
                    call.respond(HttpStatusCode.Created)
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "No se pudo actualizar la imagen")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "No se encontró ninguna imagen en la solicitud")
            }
        }

        delete("/user/{email}/deleteImage") {
            val email = call.parameters["email"]
            if (email == null) {
                call.respond(HttpStatusCode.BadRequest, "Falta el parámetro email")
                return@delete
            }

            val user = ProviderUserCase.getUserByEmail(email)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
                return@delete
            }

            if (user.imageUrl.isNullOrBlank()) {
                call.respond(HttpStatusCode.NotFound, "No se encontró una imagen para este usuario")
                return@delete
            }

            val imageFile = File(user.imageUrl)
            if (imageFile.exists()) {
                println("DEBUG: Eliminando archivo: ${user.imageUrl}")
                imageFile.delete()
            } else {
                println("DEBUG: El archivo no existe en el servidor")
            }

            val success = ProviderUserCase.deleteUserProfilePicture(email)
            if (success) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.InternalServerError, "No se pudo eliminar la imagen en la base de datos")
            }
        }

        get("/user/{email}/image") {
            val email = call.parameters["email"]
            if (email == null) {
                call.respond(HttpStatusCode.BadRequest, "Falta el parámetro email")
                return@get
            }

            val imageUrl = ProviderUserCase.getUserProfilePicture(email)
            if (imageUrl.isNullOrBlank()) {
                call.respond(HttpStatusCode.NotFound, "No se encontró una imagen para este usuario")
                return@get
            }

            val imageFile = File(imageUrl)
            if (!imageFile.exists()) {
                call.respond(HttpStatusCode.NotFound, "El archivo de imagen no existe en el servidor")
                return@get
            }

            call.respondFile(imageFile)
        }

        staticResources("/static", "static")
    }
}