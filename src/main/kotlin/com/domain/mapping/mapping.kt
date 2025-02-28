package com.domain.mapping

import com.data.persistence.models.UserDao
import com.domain.models.User

import com.domain.models.UpdateUser

fun UserDaoToUser(userDao: UserDao): User {

    val e = User(
        userDao.name,
        userDao.password,
        userDao.token ?: "null"
    )
    return e
}


fun UpdateUserToUser(user: UpdateUser): User {
    var e = User(
        user.name!!,
        user.password!!,
        user.token ?: ""
    )
    return e
}
