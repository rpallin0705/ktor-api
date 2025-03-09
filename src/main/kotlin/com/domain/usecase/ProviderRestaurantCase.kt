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

    suspend fun insertRestaurant(restaurant: Restaurant?): Restaurant? {
        if (restaurant == null) {
            logger.warn("No existe el restaurante para insertar")
            return null
        }
        return if (insertRestaurantUseCase(restaurant)) {
            restaurant
        } else {
            logger.warn("No se ha insertado el restaurante")
            null
        }
    }


    suspend fun updateRestaurant(restaurantId: Long?, restaurant: Restaurant?): Restaurant? {
        if (restaurantId == null || restaurant == null) {
            logger.warn("El restaurante o la ID no existen")
            return null
        }
        return if (updateRestaurantById(restaurantId, restaurant)) {
            restaurant
        } else {
            logger.warn("No se pudo actualizar el restaurante")
            null
        }
    }


    suspend fun deleteRestaurant(restaurantId: Long?) : Boolean {
        return deleteRestaurantById(restaurantId)
    }
}