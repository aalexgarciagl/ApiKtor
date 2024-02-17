package com.example.respuestas

import kotlinx.serialization.Serializable

@Serializable
data class Response(val message:String, val status: Int)