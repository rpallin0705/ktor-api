package com.domain.usecase.restaurant

import com.domain.repository.RestaurantInterface

class DeleteRestaurantById(val repository: RestaurantInterface) {
    var id: Long? = null
    suspend operator fun invoke(restaurantId: Long?): Boolean {
        return if (id == null) false
        else repository.deleteRestaurantById(id!!)
    }
}