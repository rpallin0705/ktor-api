package com.data.persistence.models.favorites

import com.data.persistence.models.restaurant.RestaurantDao
import com.data.persistence.models.user.UserDao
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserFavoritesDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UserFavoritesDao>(UserFavsRestaurantsTable)

    var user by UserDao referencedOn UserFavsRestaurantsTable.userId
    var restaurant by RestaurantDao referencedOn UserFavsRestaurantsTable.restaurantId
}
