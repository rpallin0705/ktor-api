package com.domain.usecase.user

import com.data.persistence.repository.PersistenceUserRepository
import com.domain.models.*


import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ProviderUserCase {

   // private val repository = MemoryUserRepository()  //me creo el repositorio con todos los datos. Lo hago una sóla vez.
    private val repository = PersistenceUserRepository()
    val logger: Logger = LoggerFactory.getLogger("UserUseCaseLogger")

    //Aquí tengo todos los casos de uso.
    private val getAllUserUseCase = GetAllUserUsersCase(repository)
    private val getUserByNameUseCase = GetUserByNameUseCase(repository)
    private val updateUserUseCase = UpdateUserUseCase(repository)
    private val insertUserUseCase = InsertUserUseCase(repository)
    private val deleteUserUseCase = DeleteUserUseCase(repository)
    private val loginUseCase = LoginUseCase(repository)
    private val registerUseCase = RegisterUseCase(repository)



    suspend fun getAllUsers() = getAllUserUseCase()  //Lo invoco, como si fuera una función.

    suspend fun getUserByName(name : String) : User? {
        if (name.isBlank()){
            logger.warn("El name está vacío. No podemos buscar un empleado")
            return null
        }
        getUserByNameUseCase.name = name
        val emp = getUserByNameUseCase()
        return if (emp == null) {
            logger.warn("No se ha encontrado un empleado con ese $name.")
            null
        }else{
            emp
        }
    }



    suspend fun insertUser(employee: User?) : Boolean{
        if (employee == null){
            logger.warn( "No existen datos del empleado a insertar")
            return false
        }
        insertUserUseCase.user = employee
        val res = insertUserUseCase()
            return if (!res){
            logger.warn("No se ha insertado el empleado. Posiblemente ya exista")
            false
        }else{
            true
        }
    }

    suspend fun updateUser(updateUser: UpdateUser?, dni : String) : Boolean{
        if (updateUser == null){
            logger.warn("No existen datos del empleado a actualizar")
            return false
        }

        updateUserUseCase.updateUser = updateUser
        updateUserUseCase.dni = dni
        return updateUserUseCase()
    }

    suspend fun deleteUser(dni : String) : Boolean{
        deleteUserUseCase.dni = dni
        return deleteUserUseCase()
    }

    suspend fun login(dni: String?, pass: String?)  = loginUseCase(dni, pass)
}