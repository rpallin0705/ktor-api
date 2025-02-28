package com.domain.repository

import com.domain.models.Restaurant

interface RestaurantInterface {
    suspend fun findAllRestaurants() : List<Restaurant>
    suspend fun findRestaurantById(id : Long) : Restaurant?
    suspend fun insertNewRestaurant(restaurant: Restaurant) : Boolean
    suspend fun deleteRestaurantById( id : Long) : Boolean
    suspend fun updateRestaurantById( id : Long, updateRestaurant: Restaurant) :  Boolean
    suspend fun findRestaurantByName(name: String): Restaurant?
}