package com.domain.models
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val email : String,
    val password : String,
    val token:String ? = null,
    val imageUrl: String? = null
)
