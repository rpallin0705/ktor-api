package com.domain.usecase.user_favorites

import com.domain.repository.UserFavoritesInterface

class GetFavoriteRestaurantsUseCase(val repository : UserFavoritesInterface) {
    suspend operator fun invoke(userId: Int) : List<Long> = repository.getFavoriteRestaurants(userId)
}