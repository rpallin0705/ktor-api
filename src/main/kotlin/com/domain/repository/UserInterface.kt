package com.domain.repository

import com.domain.models.User
import com.domain.models.UpdateUser

interface UserInterface {
    suspend fun getAllUsers () : List <User>

    suspend fun getUserByName (name : String) : User?

    suspend fun postUser(user: User) : Boolean

    suspend fun updateUser(user: UpdateUser, name:String) : Boolean

    suspend fun deleteUser(name : String) : Boolean

    suspend fun login(name: String, pass: String) : Boolean

    suspend fun register(user: UpdateUser) : User?
    suspend fun invalidateToken(username: String): Boolean
    suspend fun getUserToken(username: String): String?
    suspend fun updateUserToken(username: String, token: String): Boolean

}