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


fun UpdateUserToUser(employee: UpdateUser): User {
    var e = User(
        employee.name!!,
        employee.password!!,
        employee.token ?: ""
    )
    return e
}
