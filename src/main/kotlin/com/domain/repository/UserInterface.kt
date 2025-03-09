package com.domain.repository

import com.domain.models.Restaurant
import com.domain.models.User
import com.domain.models.UpdateUser

interface UserInterface {
    suspend fun getAllUsers(): List<User>

    suspend fun getUserByEmail(email: String): User?

    suspend fun postUser(user: User): Boolean

    suspend fun updateUser(user: UpdateUser, email: String): Boolean

    suspend fun deleteUser(email: String): Boolean

    suspend fun login(email: String, pass: String): Boolean

    suspend fun register(user: UpdateUser): User?

    suspend fun invalidateToken(email: String): Boolean

    suspend fun getUserToken(email: String): String?

    suspend fun updateUserToken(email: String, token: String): Boolean

    suspend fun validateToken(email: String, token: String): Boolean

    suspend fun uploadUserProfilePicture(email: String, imagePath: String): Boolean
    suspend fun deleteUserProfilePicture(email: String): Boolean
}