package com.data.persistence.repository

import com.domain.models.Restaurant
import com.domain.repository.RestaurantInterface

class PersistenceRestaurantRepository : RestaurantInterface{
    override suspend fun findAllRestaurants(): List<Restaurant> {
        TODO("Not yet implemented")
    }

    override suspend fun findRestaurantById(id: Long): Restaurant? {
        TODO("Not yet implemented")
    }

    override suspend fun insertNewRestaurant(restaurant: Restaurant): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRestaurantById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateRestaurantById(id: Long, updateRestaurant: Restaurant): Boolean {
        TODO("Not yet implemented")
    }
}