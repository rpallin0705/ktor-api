package com.data.persistence.models.favorites

import com.data.persistence.models.restaurant.RestaurantTable
import com.data.persistence.models.user.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class FavoritesDao(id: EntityID<Long>) : LongEntity(id) {
    companion object: LongEntityClass<FavoritesDao>(UserFavsRestaurantsTable)
    var userId by UserFavsRestaurantsTable.userId via  UserTable.id
}