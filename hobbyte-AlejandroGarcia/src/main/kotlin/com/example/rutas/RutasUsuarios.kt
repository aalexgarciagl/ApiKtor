package com.example.rutas

import com.example.controllers.ControllerUsuario
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*


fun Route.UsuariosRouting(){

    route("/users"){
        get{
            ControllerUsuario.getAllUsers(call)
        }
        post{
            ControllerUsuario.insertUser(call)
        }
    }

    route("/login/{id}"){
        post{
            ControllerUsuario.login(call)
        }

    }

    route("user/{id}"){
        get{
            ControllerUsuario.getOneUser(call)
        }
        authenticate{
            delete {
                ControllerUsuario.deleteUser(call)
            }
        }
        put{
            ControllerUsuario.updateUser(call)
        }
    }
}