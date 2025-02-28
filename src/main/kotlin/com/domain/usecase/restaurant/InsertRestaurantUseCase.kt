package com.domain.usecase.restaurant

import com.domain.models.Restaurant
import com.domain.repository.RestaurantInterface

class InsertRestaurantUseCase (val repository : RestaurantInterface) {
    suspend operator fun invoke(newRestaurant: Restaurant) : Boolean {
        return newRestaurant.let { repository.insertNewRestaurant(it) }
    }
}