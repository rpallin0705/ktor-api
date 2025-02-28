package com.domain.models
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name : String,
    //val dni : String,
    val password : String,
    val token:String ? = null
)


/*

Me daba un problema de incompatibilidad de librería y he tenido que cambiar la versión de kotlin a la 1.9.0

Necesitamos serializar los objetos kotlin en datos en JSON y viceversa.

Serializar:
    val user = User("Alice", "Team leader", Salary.High)
    val json = Json.encodeToString(user)
    println(json) // {"name":"Alice","description":"Team leader","salary":"High"}

Deserialización: Utilizamos """ para incluir caracteres especiales como la " comilla.
    val json = """{"name":"Alice","description":"Team leader","salary":"High"}"""
    val user = Json.decodeFromString<User>(json)
    println(user) // User(name=Alice, description=Team leader, salary=High)


 */
