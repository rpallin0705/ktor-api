package com.data.persistence.repository

import com.data.persistence.models.UserDao
import com.data.persistence.models.UserTable
import com.data.persistence.models.suspendTransaction
import com.data.security.PasswordHash
import com.domain.mapping.UserDaoToUser

import com.domain.models.User
import com.domain.models.UpdateUser
import com.domain.repository.UserInterface
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.update

class PersistenceUserRepository: UserInterface {

    override suspend fun getAllUsers(): List<User> {
        return suspendTransaction {
            UserDao.all().map(::UserDaoToUser)
        }
    }

    override suspend fun getUserByName(name: String): User? {
        return suspendTransaction {
            UserDao
                .find {
                    UserTable.name eq name
                }
                .limit(1)
                .map(::UserDaoToUser)
                .firstOrNull()
        }
    }

    override suspend fun postUser(user: User): Boolean {
        val em = getUserByName(user.name)
        return if (em == null) {
            suspendTransaction {
                UserDao.new {
                    this.name = user.name
                    this.password = PasswordHash.hash(user.password) //hasheo la password.
                    this.token = user.token
                }
            }
            true
        } else
            false
    }

    override suspend fun updateUser(user: UpdateUser, name: String): Boolean {
        var num = 0
        try {
            suspendTransaction {
                num = UserTable
                    .update({ UserTable.name eq name }) { stm ->
                        user.name?.let { stm[this.name] = it }
                        user.token?.let { stm[token] = it }

                    }
            }

        }catch (e:Exception){
            e.printStackTrace()
            false
        }
        return num == 1
    }


    override suspend fun deleteUser(name: String): Boolean = suspendTransaction {
        val num = UserTable
            .deleteWhere { UserTable.name eq name }
        num == 1
    }

    override suspend fun login(name: String, pass: String): Boolean {
        val user = getUserByName(name)?: return false

        return try{
            val posibleHash = PasswordHash.hash(pass)
            posibleHash == user.password
        }catch (e: Exception){
            println("Error en la autenticación: ${e.localizedMessage}")
            false
        }
    }

    override suspend fun register(user: UpdateUser): User? {

        return try {
            suspendTransaction {
                UserDao.new {
                    this.name = user.name!!
                    this.password = PasswordHash.hash(user.password!!) //hasheo la password.
                    this.token = user.token!!
                }
            }.let {
                UserDaoToUser(it)
            }
        }catch (e: Exception){
            println("Error en el registro de empleado: ${e.localizedMessage}")
            null
        }
    }
}