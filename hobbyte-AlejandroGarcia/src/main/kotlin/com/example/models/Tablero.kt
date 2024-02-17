package com.example.models
import kotlinx.serialization.Serializable


@Serializable
class Tablero(
    var id: Int,
    var estado: Int,
    var id_jugador: Int
) {
}