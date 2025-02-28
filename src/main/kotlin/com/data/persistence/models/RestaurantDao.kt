package com.data.persistence.models

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RestaurantDao(id : EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<RestaurantDao>(RestaurantTable)
    var name by RestaurantTable.name
    var phone by RestaurantTable.phone
    var address by RestaurantTable.address
    var rating by RestaurantTable.rating
    var description by RestaurantTable.description
    var image by RestaurantTable.image
}