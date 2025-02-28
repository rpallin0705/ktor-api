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

    /*
    getAllUsers():
    1.- Devuelve una lista de objetos UserDao que son los objetos de la data. Realmente, representa una fila de la tabla.
    2.- Como UserDao, hereda de IntEntity(id), indicando que la clave primera será entera y de nombre id,
    IntEntity ya incorpora metodos de acceso a los datos. Dicha clase, proviene de Exposed.
    3.- Para cada objeto que representa la fila, lo mapeamos a objeto del modelo de negocio a partir de una función de mapeo.
     */

    override suspend fun getAllUsers(): List<User> {
        return suspendTransaction {
            UserDao.all().map(::UserDaoToUser)
        }
    }


    /*
    getUserBySalary:
    1.- Devuelve una lista de UserDao representados por filas, en los que pasamos una condición de búsqueda.
    2.- La condición de búsqueda, representada por medio del método find, acepta como lambda la lógica para comparar
    aquellos registros en los que el campo salario sobre la tabla de la BBDD, sea igual al que pasamos como argumento.
    3.- Volvemos a mapear a objetos de la lógica de negocio.
    4.- Recordar que al comparar, no lo hacemos sobre el campo de UserDao, sino sobre el campo de la tabla.
     */

    /*
    getUserByName
    1.- Es exactamente igual que la anterior, solo que comparamos sobre el campo name de la tabla.
     */

    /*
    getUserByDni()
    1.- Es igual que los dos anteriores, sólo que limitamos a un sólo objeto y en caso
    de que no lo encuentre, devuelva null, porque la función debe devolver un User?
     */
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

    //método que a partir de
    override suspend fun login(name: String, pass: String): Boolean {
        val user = getUserByName(name)?: return false

        return try{
            val posibleHash = PasswordHash.hash(pass) //hasheo la password del logueo
            posibleHash == user.password //compruebo con la que hay en la BBDD
        }catch (e: Exception){
            println("Error en la autenticación: ${e.localizedMessage}")
            false
        }
    }

    /*
    Cambiaremos después algunas cosas en la bBDD


     */
    override suspend fun register(user: UpdateUser): User? {

        return try {
            suspendTransaction {
                UserDao.new {
                    this.name = user.name!! //es seguro.
                    this.password = PasswordHash.hash(user.password!!) //hasheo la password.
                    this.token = user.token!!
                }
            }.let {
                UserDaoToUser(it) //hago directamente el mapping.
            }
        }catch (e: Exception){
            println("Error en el registro de empleado: ${e.localizedMessage}")
            null
        }
    }
}