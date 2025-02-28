package com.domain.usecase.restaurant

import com.domain.models.Restaurant
import com.domain.repository.RestaurantInterface

class UpdateRestaurantById(val repository : RestaurantInterface) {
    suspend operator fun invoke(restaurantId: Long, updatedRestaurant: Restaurant) : Boolean {
            return updatedRestaurant.let { repository.updateRestaurantById(restaurantId, it) }    }
}