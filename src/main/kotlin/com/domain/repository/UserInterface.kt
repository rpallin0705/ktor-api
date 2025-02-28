package com.domain.repository

import com.domain.models.User
import com.domain.models.UpdateUser

/*
Como vamos a lanzar consultas a la BBDD, deben hacerse por medio de corrutinas.
Cuando desde una corrutina ejecuto un método, éste tiene que estar definido como suspend.
 */

interface UserInterface {
    suspend fun getAllUsers () : List <User>

    suspend fun getUserByName (name : String) : User?

    suspend fun postUser(user: User) : Boolean

    suspend fun updateUser(user: UpdateUser, dni:String) : Boolean

    suspend fun deleteUser(dni : String) : Boolean

    suspend fun login(dni: String, pass: String) : Boolean  //más adelante, implementaré con token

    suspend fun register(user: UpdateUser) : User? //Este será el que utilicemos para el registro
}