package com.data.persistence.models.user

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable("user") {
    val name = varchar("email", 100)
    val password = varchar("password", 255)
    val token = varchar("token", 255).nullable()
    val imageUrl = varchar("image_url", 500).nullable()
}