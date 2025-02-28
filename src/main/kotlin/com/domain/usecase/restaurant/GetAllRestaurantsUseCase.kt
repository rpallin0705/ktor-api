package com.domain.usecase.restaurant

import com.domain.models.Restaurant
import com.domain.repository.RestaurantInterface

class GetAllRestaurantsUseCase (val repository : RestaurantInterface) {
    suspend operator fun invoke(): List<Restaurant> = repository.findAllRestaurants()
}
