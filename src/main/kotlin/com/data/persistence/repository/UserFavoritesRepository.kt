package com.data.persistence.repository

import com.data.persistence.models.favorites.UserFavsRestaurantsTable
import com.data.persistence.models.suspendTransaction
import com.data.persistence.models.restaurant.RestaurantDao
import com.data.persistence.models.user.UserDao
import com.domain.models.UserFavorites
import com.domain.repository.UserFavoritesInterface
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.and

class UserFavoritesRepository : UserFavoritesInterface {

    override suspend fun getFavoriteRestaurants(userId: Int): List<Long> = suspendTransaction {
        UserFavoritesDao.find { UserFavsRestaurantsTable.userId eq userId }
            .map { it.restaurant.id.value }
    }

    override suspend fun toggleFavorite(userId: Int, restaurantId: Long): UserFavorites = suspendTransaction {
        val existingFavorite = UserFavoritesDao.find {
            (UserFavsRestaurantsTable.userId eq userId) and
                    (UserFavsRestaurantsTable.restaurantId eq restaurantId)
        }.firstOrNull()

        if (existingFavorite != null) {
            existingFavorite.delete()
        } else {
            UserFavoritesDao.new {
                this.user = UserDao[userId] 
                this.restaurant = RestaurantDao[restaurantId]
            }
        }

        val affectedRestaurantId = existingFavorite?.restaurant?.id?.value ?: restaurantId
        UserFavorites(userId, affectedRestaurantId)
    }

}
