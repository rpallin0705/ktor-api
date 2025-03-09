package com.domain.mapping

import com.data.persistence.models.restaurant.RestaurantDao
import com.data.persistence.models.user.UserDao
import com.domain.models.Restaurant
import com.domain.models.User

import com.domain.models.UpdateUser

fun UserDaoToUser(userDao: UserDao): User {

    val e = User(
        userDao.id.value,
        userDao.email,
        userDao.password,
        userDao.token ?: "null"
    )
    return e
}


fun UpdateUserToUser(user: UpdateUser): User {
    var e = User(
        user.id,
        user.email!!,
        user.password!!,
        user.token ?: ""
    )
    return e
}

fun RestaurantDaoToRestaurant(restaurantDao: RestaurantDao): Restaurant {
    val e = Restaurant(
        restaurantDao.id.value,
        restaurantDao.name,
        restaurantDao.address,
        restaurantDao.phone,
        restaurantDao.rating ?: 0,
        restaurantDao.description ?: "",
        restaurantDao.image ?: ""
    )
    return e
}
