package com.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id: Long? = null,
    val name: String,
    val address: String,
    val phone: String,
    val rating: Int? = null,
    val description: String? = null,
    val image: String? = null
)