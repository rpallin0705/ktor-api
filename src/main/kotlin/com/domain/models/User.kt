package com.domain.models
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email : String,
    val password : String,
    val token:String ? = null
)
