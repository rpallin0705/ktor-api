package com.domain.usecase.user

import com.domain.models.Restaurant
import com.domain.models.User
import com.domain.repository.UserInterface

class GetUserFavRestaurantsUseCase(val repository: UserInterface) {
    suspend operator fun invoke(email: String): List<Restaurant> {
        return repository.getUserFavsRestaurants(email)
    }
}