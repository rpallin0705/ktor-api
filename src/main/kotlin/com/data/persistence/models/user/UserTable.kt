package com.data.persistence.models.user

import com.data.persistence.models.favorites.UserFavsRestaurantsTable.integer
import com.data.persistence.models.favorites.UserFavsRestaurantsTable.nullable
import com.data.persistence.models.favorites.UserFavsRestaurantsTable.varchar
import org.jetbrains.exposed.dao.id.IntIdTable


object  UserTable: IntIdTable("user") {
    val name = varchar("email", 100)
    val password = varchar("password", 255)
    val token = varchar("token", 255).nullable()

}