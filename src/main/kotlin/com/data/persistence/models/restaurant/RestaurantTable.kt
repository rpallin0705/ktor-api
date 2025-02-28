package com.data.persistence.models.restaurant

import org.jetbrains.exposed.dao.id.LongIdTable

object RestaurantTable : LongIdTable("restaurant") {
    val name = varchar("name", 128).uniqueIndex()
    val address = varchar("address", 255)
    val phone = varchar("phone", 9).uniqueIndex()
    val description = varchar("description", 255).nullable()
    val rating = integer("rating").nullable()
    val image = varchar("image", 255).nullable()
}