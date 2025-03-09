package com.domain.models
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID

@Serializable
data class User(
    val id: Int? = null,
    val email : String,
    val password : String,
    val token:String ? = null
)
