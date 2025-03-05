package com.domain.usecase

import com.data.persistence.repository.PersistenceUserRepository
import com.domain.models.*
import com.domain.usecase.user.*
import org.jetbrains.exposed.sql.resolveColumnType


import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ProviderUserCase {

    private val repository = PersistenceUserRepository()
    val logger: Logger = LoggerFactory.getLogger("UserUseCaseLogger")

    private val getAllUserUseCase = GetAllUserUsersCase(repository)
    private val getUserByNameUseCase = GetUserByNameUseCase(repository)
    private val updateUserUseCase = UpdateUserUseCase(repository)
    private val insertUserUseCase = InsertUserUseCase(repository)
    private val deleteUserUseCase = DeleteUserUseCase(repository)
    private val loginUseCase = LoginUseCase(repository)
    private val registerUseCase = RegisterUseCase(repository)
    private val logoutUseCase = LogoutUseCase(repository)
    private val isTokenValidUseCase = IsTokenValidUseCase(repository)
    private val getUserFavRestaurantsUseCase = GetUserFavRestaurantsUseCase(repository)

    suspend fun getAllUsers() = getAllUserUseCase()

    suspend fun getUserByEmail(email : String) : User? {
        if (email.isBlank()){
            logger.warn("El email está vacío. No podemos buscar un empleado")
            return null
        }
        getUserByNameUseCase.email = email
        val emp = getUserByNameUseCase()
        return if (emp == null) {
            logger.warn("No se ha encontrado un empleado con ese $email.")
            null
        }else{
            emp
        }
    }

    suspend fun insertUser(user: User?) : Boolean{
        if (user == null){
            logger.warn( "No existen datos del empleado a insertar")
            return false
        }
        insertUserUseCase.user = user
        val res = insertUserUseCase()
            return if (!res){
            logger.warn("No se ha insertado el empleado. Posiblemente ya exista")
            false
        }else{
            true
        }
    }

    suspend fun updateUser(updateUser: UpdateUser?, email : String) : Boolean{
        if (updateUser == null){
            logger.warn("No existen datos del empleado a actualizar")
            return false
        }

        updateUserUseCase.updateUser = updateUser
        updateUserUseCase.email = email
        return updateUserUseCase()
    }

    suspend fun deleteUser(email : String) : Boolean{
        deleteUserUseCase.email = email
        return deleteUserUseCase()
    }
    
    suspend fun getUserFavs(email : String) : List<Restaurant>{
        val user = getUserByEmail(email)
        return if(user != null) {
            getUserFavRestaurantsUseCase(email)
        } else
            getUserFavRestaurantsUseCase("")
    }

    suspend fun login(email: String?, pass: String?)  = loginUseCase(email, pass)
    suspend fun register (newUser: UpdateUser) = registerUseCase(newUser)
    suspend fun logout(email: String): Boolean = logoutUseCase(email)
    suspend fun isTokenValid(email: String, token: String) = isTokenValidUseCase(email, token)
}