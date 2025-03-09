package com.data.persistence.repository

import com.data.persistence.models.user.UserDao
import com.data.persistence.models.user.UserTable
import com.data.persistence.models.suspendTransaction
import com.data.security.PasswordHash
import com.domain.mapping.UserDaoToUser

import com.domain.models.User
import com.domain.models.UpdateUser
import com.domain.repository.UserInterface
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.update

class PersistenceUserRepository : UserInterface {

    override suspend fun getAllUsers(): List<User> {
        return suspendTransaction {
            UserDao.all().map(::UserDaoToUser)
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return suspendTransaction {
            UserDao
                .find {
                    UserTable.name eq email
                }
                .limit(1)
                .map(::UserDaoToUser)
                .firstOrNull()
        }
    }

    override suspend fun postUser(user: User): Boolean {
        val em = getUserByEmail(user.email)
        return if (em == null) {
            suspendTransaction {
                UserDao.new {
                    this.email = user.email
                    this.password = PasswordHash.hash(user.password)
                    this.token = user.token
                }
            }
            true
        } else
            false
    }

    override suspend fun updateUser(user: UpdateUser, email: String): Boolean {
        var num = 0
        try {
            suspendTransaction {
                num = UserTable
                    .update({ UserTable.name eq email }) { stm ->
                        user.email?.let { stm[this.name] = it }
                        user.password?.let { stm[password] = it }
                    }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
        return num == 1
    }

    override suspend fun deleteUser(email: String): Boolean = suspendTransaction {
        val num = UserTable
            .deleteWhere { UserTable.name eq email }
        num == 1
    }

    override suspend fun login(email: String, pass: String): Boolean {
        val user = getUserByEmail(email) ?: return false

        return try {
            val posibleHash = PasswordHash.hash(pass)
            posibleHash == user.password
        } catch (e: Exception) {
            println("Error en la autenticación: ${e.localizedMessage}")
            false
        }
    }

    override suspend fun register(user: UpdateUser): User? {
        return suspendTransaction {
            try {
                val hashedPassword = PasswordHash.hash(user.password!!)

                val newUser = UserDao.new {
                    email = user.email!!
                    password = hashedPassword
                    token = ""
                }
                commit()
                UserDaoToUser(newUser)
            } catch (e: Exception) {
                rollback()
                println("Error al registrar usuario: ${e.message}")
                null
            }
        }
    }


    override suspend fun invalidateToken(email: String): Boolean {
        return suspendTransaction {
            val user = UserDao.find { UserTable.name eq email }.singleOrNull()
            if (user != null) {
                user.token = ""
                true
            } else {
                false
            }
        }
    }

    override suspend fun getUserToken(email: String): String? {
        return suspendTransaction {
            UserDao.find { UserTable.name eq email }
                .singleOrNull()?.token
        }
    }

    override suspend fun updateUserToken(email: String, token: String): Boolean {
        return suspendTransaction {
            val user = UserDao.find { UserTable.name eq email }.singleOrNull()
            if (user != null) {
                user.token = token
                true
            } else {
                false
            }
        }
    }

    override suspend fun validateToken(email: String, token: String): Boolean {
        return suspendTransaction {
            val storedToken = UserDao.find { UserTable.name eq email }
                .singleOrNull()?.token
            storedToken == token
        }
    }
}