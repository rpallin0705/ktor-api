package com.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserFavorites (
    val userId: Int,
    val restaurantId: Long,
)