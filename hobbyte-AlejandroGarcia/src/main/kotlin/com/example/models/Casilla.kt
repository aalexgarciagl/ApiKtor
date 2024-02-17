package com.example.models

import kotlinx.serialization.Serializable


@Serializable
class Casilla(
    var id: Int,
    var id_prueba: Int,
    var id_tablero: Int,
    var capacidad_requerida: Int,
    var estado: Int
) {

    //Estados:
    //0 = sin jugar
    //1 = ganada
    //2 = perdida
}