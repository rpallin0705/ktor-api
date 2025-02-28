package com.ktor.router

import com.domain.models.User
import com.domain.models.UpdateUser
import com.domain.usecase.user.ProviderUserCase
import com.domain.usecase.user.ProviderUserCase.logger
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRouting() {
    routing {
        /*
        ktor evalua los endpoint por orden.
         */

        get("/") {
            call.respondText("Hello World!")
        }

        
        get("/user"){

            val userName = call.request.queryParameters["name"]
            logger.warn("El Name tiene de valor $userName")
            if (userName != null) {
                val user = ProviderUserCase.getUserByName(userName)
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound, "usuario no encontrado")
                } else {
                    call.respond(user)
                }
                return@get
            }

        }

        /*
        Endpoint que no es recomendable, porque no se debe utilizar parámetros de url para filtrar. Para eso están los de consulta.
         */
        get("/user/{userName}") {

            //Comprobamos si se ha pasado el name por parámetro
            val userName = call.parameters["userName"]
            if (userName == null){
                call.respond(HttpStatusCode.BadRequest, "Debes pasar el name a buscar") //Montamos una respuesta con código 400.
                return@get  //finalizamos en endpoint y mandamos inmediantamente la respuesta.
            }

            val user = ProviderUserCase.getUserByName(userName)
            if (user ==null){
                call.respond(HttpStatusCode.NotFound,"usuario no encontrado")  //Montamos un 404 de no encontrado.
                return@get //finalizamos en endpoint y mandamos inmediantamente la respuesta.
            }
            call.respond(user)  //mandamos el usuario como respuesta al cliente.
        }




        post("/user"){
            try{
                val emp = call.receive<User>()  //Leemos el cuerpo de la solicitud como un objeto User
                val res = ProviderUserCase.insertUser(emp)
                if (! res){
                    call.respond(HttpStatusCode.Conflict, "El usuario no pudo insertarse. Puede que ya exista")
                    return@post //aunque no es necesario, es buena práctica ponerlo para no olvidarlo, pero no hay más lógica.
                }
                call.respond(HttpStatusCode.Created, "Se ha insertado correctamente con nombre =  ${emp.name}")
            } catch (e : IllegalStateException){
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de datos o lectura del cuerpo.")
            } catch (e:JsonConvertException){
                call.respond(HttpStatusCode.BadRequest," Problemas en la conversión json")
            } catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest, "Error en los datos. Probablemente falten.")
            }


        }

        patch("/user/{userName}") {
            try{
                val name = call.parameters["userName"]
                name?.let{
                    val updateUser = call.receive<UpdateUser>()
                    val res = ProviderUserCase.updateUser(updateUser, name)
                    if (! res){
                        call.respond(HttpStatusCode.Conflict, "El usuario no pudo modificarse. Puede que no exista")
                        return@patch //aunque no es necesario, es buena práctica ponerlo para no olvidarlo, pero no hay más lógica.
                    }
                    call.respond(HttpStatusCode.Created, "Se ha actualizado correctamente con name =  ${name}")
                }?: run{
                    call.respond(HttpStatusCode.BadRequest,"Debes identificar el usuario")
                    return@patch //aunque no es necesario, es buena práctica ponerlo para no olvidarlo, pero no hay más lógica.
                }
            } catch (e: IllegalStateException){
                call.respond(HttpStatusCode.BadRequest,"Error en el formado de envío de los datos o lectura del cuerpo.")
            } catch (e: JsonConvertException){
                call.respond(HttpStatusCode.BadRequest,"Error en el formado de json")
            }
        }


        delete("/user/{userName}") {
            val name = call.parameters["userName"]
            logger.warn("Queremos borrar el usuario con name $name")
            name?.let{
                val res = ProviderUserCase.deleteUser(name)
                if (! res){
                    call.respond(HttpStatusCode.NotFound,"usuario no encontrado para borrar")  //Montamos un 404 de no encontrado.
                }else{
                    call.respond(HttpStatusCode.NoContent,)
                }
            }?:run{
                call.respond(HttpStatusCode.NoContent,"Debes identintificar el usuario")
            }
            return@delete
        }


        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }

    routing {
        post ("/auth"){
            val loginRequest = call.receive<UpdateUser>()
            val isLogin = ProviderUserCase.login(loginRequest.name, loginRequest.password)

            if (isLogin)
                call.respond(HttpStatusCode.OK,"Usuario con name ${loginRequest.name} logueado")
            else
                call.respond(HttpStatusCode.Unauthorized, "Usuario incorrecto")
        }

  /*       post ("/register"){
            try{
                val user = call.receive<UpdateUser>()
                val register = ProviderUserCase.(user)

                if (register != null)
                    call.respond(HttpStatusCode.Created, "Se ha insertado correctamente con name =  ${register.name}")
                else
                    call.respond(HttpStatusCode.Conflict, "No se ha podido realizar el registro")
            } catch (e : IllegalStateException){
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de datos o lectura del cuerpo.")
            } catch (e:JsonConvertException){
                call.respond(HttpStatusCode.BadRequest," Problemas en la conversión json")
            }

        }*/

//Necesito hacer una carga de usuarios...... Para mas adelante.

    } //fin routing
}



/*
1.- Utilizaremos un directorio para servir recursos estáticos, como imágenes, html, css, javaScript
    - Primer parámetro static, como prefijo de la url. http://localhost/static
    - Segundo parámetro static, como directorio dentro del proyecto donde buscará el recurso. Lo llamamos static.
    - Tenemos un fichero index.html dentro de static. Lo utilizaremos más adelante.

2.- Añadimos una ruta con user. Ejemplo http://localhost/user
   - Necesitamos mandar una solicitud HTTP que el servidor deberá manejar. La solicitud es de tipo respond. Significa
   que el servidor mandará una respuesta al cliente. En dicha respuesta, tenemos una lista de objetos User
   que al tener la serialización, la mandará en formato json. Para ello, utiliza el pluggin de serialización (ContentNegotiation)
   configurada previamente para convertir automáticamente los objetos kotlin en json.

   - El flujo es:
        1.- El cliente hace una solicitud GET a user
        2.- ktor encuentra la routa get("/user")
        3.- El servidor crea una lista de objetos.
        4.- La lista se convierte automaticamente en Json gracias a CpontentNegotiation.
        5.- El servidor manda una respuesta al cliente.

        El pluggin Serialization, define como convertir los objetos kotlin a json y viceversa.
        El pluggin ContentNegotiation, gestiona el formato de respuesta o solicitudes dependiendo de las cabeceras
            enviadas por el cliente. HTTP Acept. Si el cliente envía Accpt:application/json, éste responderá automáticamente
            en json. Necesitaríamos otro Serializacón para xml y por tanto añadir soporte XML en el plugin ContentNegotiation.

        Por defecto, el servidor enviará la respuesta en JSON si el cliente en su solicitud no incluye una cabecera
        Accept con application/json
 */