package com.data.inmemory.repository

import com.data.inmemory.models.UserData
import com.domain.models.User

import com.domain.models.UpdateUser
import com.domain.repository.UserInterface

/*
Marca el acceso a datos dependiendo del contrato. Será la implementación de acceso a Memoria.
 */
class MemoryUserRepository : UserInterface {

    override suspend fun getAllUsers(): List<User> {
        return UserData.listUser
    }




    override suspend fun getUserByName(name: String): User? {
        return UserData.listUser.first { it.name == name }
    }



    override suspend fun postUser(employee: User) : Boolean{
        val emp = getUserByName(employee.name
        )
        return if (emp!= null) {
            false
        } else{
                UserData.listUser.add(employee)
                true
            }
    }


    /*
    Buscamos el empleado a modificar y sobreescribimos el mismo objeto con los datos modificados.
    Para ello, utilizamos el método copy que tiene cualquier objeto.
    Os recuerdo que el copy, vuelve a referenciar al objeto, por eso hay que sobreescribirlo en la lista.
     */
    override suspend fun updateUser(updateUser: UpdateUser, name:String) : Boolean{
        val index = UserData.listUser.indexOfFirst { it.name == name }
        return if (index != -1) {
            val originUser = UserData.listUser[index]
            UserData.listUser[index] =  originUser
                .copy(
                    name = updateUser.name ?: originUser.name,
                    token = updateUser.token ?: originUser.token,
                )
            true
        }
        else{
            false
        }
    }

    override suspend fun deleteUser(name: String): Boolean {
        val index = UserData.listUser.indexOfFirst { it.name == name }
        return if (index != -1) {
            UserData.listUser.removeAt(index)
            true
        }else{
            false
        }
    }

    override suspend fun login(dni: String, pass: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun register(employee: UpdateUser): User? {
        TODO("Not yet implemented")
    }
}