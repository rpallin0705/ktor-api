package com.domain.usecase

import com.data.persistence.repository.UserFavoritesRepository
import com.domain.models.UserFavorites
import com.domain.usecase.user_favorites.GetFavoriteRestaurantsUseCase
import com.domain.usecase.user_favorites.ToggleFavoritesUseCase
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ProviderUserFavoritesCase {

    private val repository = UserFavoritesRepository()
    val logger: Logger = LoggerFactory.getLogger(ProviderUserFavoritesCase::class.java)

    private val getUserFavoriteRestaurantsUseCase = GetFavoriteRestaurantsUseCase(repository)
    private val toggleFavoritesUseCase = ToggleFavoritesUseCase(repository)

    suspend fun getFavoriteRestaurants(userId: Int): List<Long> {
        if (userId == null) {
            logger.warn("Id de usuario no existente")
            return emptyList()
        }
        return getUserFavoriteRestaurantsUseCase(userId)
    }

    suspend fun toggleFavorites(userId: Int, restaurantId: Long): UserFavorites? {
        if (userId == null || restaurantId == null) {
            logger.warn("Id de usuario o restaurante no existente")
            return null
        }
        return toggleFavoritesUseCase(userId, restaurantId)
    }

}