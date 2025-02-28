package com.domain.models

import kotlinx.serialization.Serializable

/*
Sólo para serializar en consultas
patch
 */
@Serializable
data class UpdateUser (
    var password: String? = null,
    var name: String? = null,
    var token:String ? = null
)