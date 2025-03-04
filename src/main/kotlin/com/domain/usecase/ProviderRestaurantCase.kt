package com.domain.usecase

import com.data.persistence.repository.PersistenceRestaurantRepository
import com.domain.models.Restaurant
import com.domain.usecase.restaurant.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ProviderRestaurantCase {
    private val repository = PersistenceRestaurantRepository()
    val logger: Logger = LoggerFactory.getLogger("RestaurantUseCaseLogger")

    private val getAllRestaurantsUseCase = GetAllRestaurantsUseCase(repository)
    private val getRestaurantByIdUseCase = GetRestaurantByIdUseCase(repository)
    private val insertRestaurantUseCase = InsertRestaurantUseCase(repository)
    private val deleteRestaurantById = DeleteRestaurantById(repository)
    private val updateRestaurantById = UpdateRestaurantById(repository)

    suspend fun getAllRestaurants() = getAllRestaurantsUseCase()

    suspend fun getRestaurantsById(restaurantId: Long?): Restaurant? {
        if (restaurantId == null) {
            logger.warn("El id no se ha recibido correctamente")
            return null
        }
        val emp = getRestaurantByIdUseCase(restaurantId)
        return if (emp == null) {
            logger.warn("No se encontró en restaurante")
            null
        } else
            emp
    }

    suspend fun insertRestaurant(restaurant: Restaurant?) : Boolean {
        if(restaurant == null) {
            logger.warn("No existe el restaurante para insertar")
            null
        }
        val res = insertRestaurantUseCase(restaurant!!)
        return if (!res){
            logger.warn("No se ha insertado el restaurante")
            false
        } else {
             true
        }
    }

    suspend fun updateRestaurant(restaurantId: Long? ,restaurant: Restaurant?) : Boolean {
        if (restaurantId == null || restaurant == null) {
            logger.warn("El restaurante o la id no existen")
            return false
        }
        return updateRestaurantById(restaurantId, restaurant)
    }

    suspend fun deleteRestaurant(restaurantId: Long?) : Boolean {
        return deleteRestaurantById(restaurantId)
    }
}