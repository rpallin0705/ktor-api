package com.data.persistence.models.user

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDao>(UserTable)
    var email by UserTable.name
    var password by UserTable.password
    var token by UserTable.token
    var imageUrl by UserTable.imageUrl
}