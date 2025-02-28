package com.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    val id : Long,
    val name : String,
    val address : String,
    val phone : String,
    val description : String,
    val rating : Double,
    val image : String
)