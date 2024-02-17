package com.example.models

import kotlinx.serialization.Serializable


@Serializable
class Usuario(
    var id: Int? = null,
    var nombre: String,
    var victorias: Int? = null
) {
}