package com.example.respuestas

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val success: Boolean, val token: String)