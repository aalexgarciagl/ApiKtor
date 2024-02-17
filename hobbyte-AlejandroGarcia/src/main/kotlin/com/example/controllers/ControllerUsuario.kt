package com.example.controllers

import com.example.database.ConexionBD
import com.example.models.Usuario
import com.example.respuestas.LoginResponse
import com.example.respuestas.Response
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import utils.TokenManager

object ControllerUsuario {

    //El login funciona con la id y el nombre actuando como si fuera la password.
    suspend fun login(call: ApplicationCall){
        var tokenManager = TokenManager()
        val user = call.receive<Usuario>()
        val id_user = call.parameters["id"]?.toInt()
        if (id_user != null) {
            var usuario = ConexionBD.obtenerUser(id_user)
            if (usuario != null) {
                if(usuario.nombre == user.nombre) {
                    val token = tokenManager.generateJWTToken(usuario)
                    val loginResponse = LoginResponse(success = true, token = token)
                    call.respond(loginResponse)
                } else {
                    val response = Response("login incorrecto", 400)
                    call.respond(response)
                }
            } else {
                call.respond("Usuario no encontrado")
            }
        } else {
            call.respond("Id no encontrada")
        }
    }


    suspend fun getAllUsers(call: ApplicationCall){
        var usuarios = ConexionBD.obtenerUsuarios()
        call.respond(usuarios)
    }

    suspend fun getOneUser(call: ApplicationCall){
        val id_user = call.parameters["id"]?.toInt()
        if (id_user != null) {
            var usuario = ConexionBD.obtenerUser(id_user)
            if (usuario != null) {
                call.respond(usuario)
            } else {
                call.respond("Usuario no encontrado")
            }
        } else {
            call.respond("Id no encontrada")
        }
    }

    suspend fun insertUser(call: ApplicationCall){
        val user = call.receive<Usuario>()
        ConexionBD.insertarUser(user)
        call.respond("Usuario insertado con exito")
    }

    suspend fun deleteUser(call: ApplicationCall){
        val id_user = call.parameters["id"]?.toInt()
        if (id_user != null) {
            ConexionBD.eliminarUser(id_user)
            call.respond("Usuario eliminado con exito")
        } else {
            call.respond("Id no encontrada")
        }
    }

    suspend fun updateUser (call: ApplicationCall){
        val id_user = call.parameters["id"]?.toInt()
        if (id_user != null) {
            val user = call.receive<Usuario>()
            ConexionBD.modificarUser(id_user, user)
            call.respond("Usuario modificado con exito")
        } else {
            call.respond("Id no encontrada")
        }
    }
}