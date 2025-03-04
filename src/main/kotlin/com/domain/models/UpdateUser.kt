package com.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUser (
    var password: String? = null,
    var email: String? = null,
    var token:String ? = null
)