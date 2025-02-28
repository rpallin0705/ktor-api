package com.domain.usecase.restaurant

import com.domain.models.Restaurant
import com.domain.repository.RestaurantInterface

class GetRestaurantByIdUseCase(val repository : RestaurantInterface) {
    suspend operator fun invoke(restaurantId: Long): Restaurant? {
        return restaurantId.let {  repository.findRestaurantById(it) }
    }
}