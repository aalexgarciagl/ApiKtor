package com.example.rutas

import com.example.controllers.ControllerPartida
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*


fun Route.PartidasRouting(){

    route("iniciarTablero/{id}"){
        authenticate{
            post {
                ControllerPartida.iniciarTablero(call)
            }
        }
    }

    route("mostrarCasillasTablero/{id_tablero}"){
        get{
            ControllerPartida.mostrarTablero(call)
        }
    }

    route("tablerosJugador/{id}"){
        get{
            ControllerPartida.mostrarTableros(call)
        }
    }

    route("/destaparCasilla/{id_tablero}/{cDestapada}"){
        put{
            ControllerPartida.destaparCasilla(call)
        }
    }
}