package com.data.persistence.models.favorites

import com.data.persistence.models.restaurant.RestaurantTable
import com.data.persistence.models.user.UserTable
import org.jetbrains.exposed.dao.id.LongIdTable

object UserFavsRestaurantsTable : LongIdTable("user_favorites") {
    val userId = reference("user_id", UserTable)
    val restaurantId = reference("restaurant_id", RestaurantTable)
}