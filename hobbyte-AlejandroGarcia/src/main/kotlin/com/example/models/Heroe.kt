package com.example.models

import kotlinx.serialization.Serializable


@Serializable
class Heroe(
    var id: Int,
    var nombre: String,
    var id_prueba: Int,
    var capacidad: Int
)
{


}