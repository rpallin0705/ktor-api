package com.domain.repository

import com.domain.models.Restaurant
import com.domain.models.UpdateUser
import com.domain.models.UserFavorites

interface UserFavoritesInterface {
    suspend fun getFavoriteRestaurants(userId: Int): List<Long>
    suspend fun toggleFavorite(userId: Int, restaurantId: Long): UserFavorites
}