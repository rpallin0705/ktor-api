package com.data.persistence.models

import org.jetbrains.exposed.dao.id.IntIdTable

/*
 * Este objeto define la estructura de la tabla 'user' en la base de datos.
 * Exposed es un ORM (Object-Relational Mapping)
 * Le dice a Exposed, cual es la estructrura que tiene la tabla user y la necesita
 * para saber como se almacenan los datos. ---> Información que necesita Exposed de la estructura de cada tabla.
 *
 * Nuestro objeto debe ser de tipo IntIdTable("Nombre de la tabla"), en la que significa que la clave
 * primaria será de tipo entero y se llamara id.
 */

object  UserTable: IntIdTable("User") {
  //  val id = integer("id").autoIncrement()// Clave primaria
    //val dni = varchar("dni", 20).uniqueIndex() // Campo único
    val name = varchar("name", 100) // Campo opcional
    val password = varchar("password", 255)
    val token = varchar("token", 255).nullable()

}