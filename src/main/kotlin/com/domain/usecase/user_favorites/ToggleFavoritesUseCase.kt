package com.domain.usecase.user_favorites

import com.domain.models.UserFavorites
import com.domain.repository.UserFavoritesInterface

class ToggleFavoritesUseCase(val repository: UserFavoritesInterface) {
    suspend operator fun invoke(userId: Int, restaurantId: Long) : UserFavorites = repository.toggleFavorite(userId, restaurantId)
}