package com.data.inmemory.repository

import com.data.inmemory.models.UserData
import com.domain.models.Restaurant
import com.domain.models.User

import com.domain.models.UpdateUser
import com.domain.repository.UserInterface

class MemoryUserRepository : UserInterface {

    override suspend fun getAllUsers(): List<User> {
        return UserData.listUser
    }

    override suspend fun getUserByEmail(email: String): User? {
        return UserData.listUser.first { it.email == email }
    }

    override suspend fun postUser(user: User) : Boolean{
        val emp = getUserByEmail(user.email
        )
        return if (emp!= null) {
            false
        } else{
                UserData.listUser.add(user)
                true
            }
    }

    override suspend fun updateUser(updateUser: UpdateUser, email:String) : Boolean{
        val index = UserData.listUser.indexOfFirst { it.email == email }
        return if (index != -1) {
            val originUser = UserData.listUser[index]
            UserData.listUser[index] =  originUser
                .copy(
                    email = updateUser.email ?: originUser.email,
                    token = updateUser.token ?: originUser.token,
                )
            true
        }
        else{
            false
        }
    }

    override suspend fun deleteUser(email: String): Boolean {
        val index = UserData.listUser.indexOfFirst { it.email == email }
        return if (index != -1) {
            UserData.listUser.removeAt(index)
            true
        }else{
            false
        }
    }

    override suspend fun login(email: String, pass: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun register(user: UpdateUser): User? {
        TODO("Not yet implemented")
    }

    override suspend fun invalidateToken(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUserToken(email: String): String? {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserToken(email: String, token: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun validateToken(email: String, token: String): Boolean {
        TODO("Not yet implemented")
    }

}