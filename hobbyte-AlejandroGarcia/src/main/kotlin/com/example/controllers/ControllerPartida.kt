package com.example.controllers

import com.example.database.ConexionBD
import com.example.models.Casilla
import com.example.models.Heroe
import com.example.models.Tablero
import com.example.respuestas.Response
import io.ktor.server.application.*
import io.ktor.server.response.*

object ControllerPartida {

    suspend fun mostrarTablero(call: ApplicationCall){
        val id_tablero = call.parameters["id_tablero"]?.toInt()
        val casillas = ConexionBD.obtenerCasillasTablero(id_tablero)
        if (casillas.isEmpty()){
            val response = Response("Tablero no encontrado", 404)
            call.respond(response)
        }else{
            call.respond(casillas)
        }
    }

    suspend fun destaparCasilla(call: ApplicationCall) {
        var tableroEntero = ConexionBD.obtenerUnTablero(call.parameters["id_tablero"]?.toInt())
        var heroes = ConexionBD.obtenerHeroes()
        var pruebas = ConexionBD.obtenerPruebas()
        var tablero = ConexionBD.obtenerCasillasTablero(call.parameters["id_tablero"]?.toInt())
        val cDestapada = call.parameters["cDestapada"]!!.toInt()


        if(tableroEntero.estado == 0){
            if (cDestapada >= 0 && cDestapada < tablero.size && tablero[cDestapada].estado == 0) {
                var pruebaSeleccionada = tablero[cDestapada]
                for (heroe in heroes) {
                    if (heroe.id_prueba == pruebaSeleccionada.id_prueba) {
                        for (prueba in pruebas) {
                            if (prueba.id == pruebaSeleccionada.id_prueba) {
                                if (heroe.capacidad > pruebaSeleccionada.capacidad_requerida) {
                                    //El 90% de las veces
                                    if (Math.random() < 0.9) {
                                        pruebaSeleccionada.estado = 1
                                        ConexionBD.actualizarEstadoCasilla(pruebaSeleccionada.id, 1)
                                        heroe.capacidad -= pruebaSeleccionada.capacidad_requerida
                                        ConexionBD.actualizarCapacidadHeroe(heroe.id, heroe.capacidad)
                                        if(comprobarPartida(tablero,heroes) == 1){
                                            tableroEntero.estado = 1
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,1)
                                            ConexionBD.sumarVictoria(tableroEntero.id_jugador)
                                            val response = Response(
                                                "Has ganado la partida!!",
                                                200)
                                            call.respond(response)
                                        }else if (comprobarPartida(tablero,heroes) == 0){
                                            val response = Response(
                                                "${heroe.nombre} ha ganado prueba de ${prueba.nombre}",
                                                200)
                                            call.respond(response)
                                        }else{
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,2)
                                            val response = Response(
                                                "Has perdido la partida",
                                                200)
                                            call.respond(response)
                                        }
                                    } else {
                                        pruebaSeleccionada.estado = 2
                                        ConexionBD.actualizarEstadoCasilla(pruebaSeleccionada.id, 2)
                                        heroe.capacidad = 0
                                        ConexionBD.actualizarCapacidadHeroe(heroe.id, heroe.capacidad)
                                        if(comprobarPartida(tablero,heroes) == 1){
                                            tableroEntero.estado = 1
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,1)
                                            ConexionBD.sumarVictoria(tableroEntero.id_jugador)
                                            val response = Response(
                                                "Has ganado la partida!!",
                                                200)
                                            call.respond(response)
                                        }else if (comprobarPartida(tablero,heroes) == 0){
                                            val response =
                                                Response(
                                                    "${heroe.nombre} ha perdido prueba de ${prueba.nombre}",
                                                    200)
                                            call.respond(response)
                                        }else{
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,2)
                                            val response = Response(
                                                "Has perdido la partida",
                                                200)
                                            call.respond(response)
                                        }
                                    }
                                } else if (heroe.capacidad == pruebaSeleccionada.capacidad_requerida) {
                                    //El 70% de las veces
                                    if (Math.random() < 0.7) {
                                        pruebaSeleccionada.estado = 1
                                        ConexionBD.actualizarEstadoCasilla(pruebaSeleccionada.id, 1)
                                        heroe.capacidad -= pruebaSeleccionada.capacidad_requerida
                                        ConexionBD.actualizarCapacidadHeroe(heroe.id, heroe.capacidad)
                                        if(comprobarPartida(tablero,heroes) == 1){
                                            tableroEntero.estado = 1
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,1)
                                            ConexionBD.sumarVictoria(tableroEntero.id_jugador)
                                            val response = Response(
                                                "Has ganado la partida!!",
                                                200)
                                            call.respond(response)
                                        }else if(comprobarPartida(tablero,heroes) == 0){
                                            val response = Response(
                                                "${heroe.nombre} ha ganado prueba de ${prueba.nombre}",
                                                200)
                                            call.respond(response)
                                        }else{
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,2)
                                            val response = Response(
                                                "Has perdido la partida",
                                                200)
                                            call.respond(response)
                                        }
                                    } else {
                                        pruebaSeleccionada.estado = 2
                                        ConexionBD.actualizarEstadoCasilla(pruebaSeleccionada.id, 2)
                                        heroe.capacidad = 0
                                        ConexionBD.actualizarCapacidadHeroe(heroe.id, heroe.capacidad)
                                        if(comprobarPartida(tablero,heroes) == 1){
                                            tableroEntero.estado = 1
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,1)
                                            ConexionBD.sumarVictoria(tableroEntero.id_jugador)
                                            val response = Response(
                                                "Has ganado la partida!!",
                                                200)
                                            call.respond(response)
                                        }else if(comprobarPartida(tablero,heroes) == 0){
                                            val response =
                                                Response(
                                                    "${heroe.nombre} ha perdido prueba de ${prueba.nombre}",
                                                    200)
                                            call.respond(response)
                                        }else{
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,2)
                                            val response = Response(
                                                "Has perdido la partida",
                                                200)
                                            call.respond(response)
                                        }
                                    }
                                } else if (heroe.capacidad < pruebaSeleccionada.capacidad_requerida && heroe.capacidad > 0) {
                                    //El 50% de las veces
                                    if (Math.random() < 0.5) {
                                        pruebaSeleccionada.estado = 1
                                        ConexionBD.actualizarEstadoCasilla(pruebaSeleccionada.id, 1)
                                        //Ya que al ganar se le resta, para no tener un numero negativo he decidido meterle
                                        //directamente que se le ponga la capacidad en 0
                                        heroe.capacidad = 0
                                        ConexionBD.actualizarCapacidadHeroe(heroe.id, heroe.capacidad)
                                        if(comprobarPartida(tablero,heroes) == 1){
                                            tableroEntero.estado = 1
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,1)
                                            ConexionBD.sumarVictoria(tableroEntero.id_jugador)
                                            val response = Response(
                                                "Has ganado la partida!!",
                                                200)
                                            call.respond(response)
                                        }else if (comprobarPartida(tablero,heroes) == 0){
                                            val response = Response(
                                                "${heroe.nombre} ha ganado prueba de ${prueba.nombre} pero ha perdido su capacidad!!",
                                                200)
                                            call.respond(response)
                                        }else{
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,2)
                                            val response = Response(
                                                "Has perdido la partida",
                                                200)
                                            call.respond(response)
                                        }
                                    } else {
                                        pruebaSeleccionada.estado = 2
                                        ConexionBD.actualizarEstadoCasilla(pruebaSeleccionada.id, 2)
                                        heroe.capacidad = 0
                                        ConexionBD.actualizarCapacidadHeroe(heroe.id, heroe.capacidad)
                                        if(comprobarPartida(tablero,heroes) == 1){
                                            tableroEntero.estado = 1
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,1)
                                            ConexionBD.sumarVictoria(tableroEntero.id_jugador)
                                            val response = Response(
                                                "Has ganado la partida!!",
                                                200)
                                            call.respond(response)
                                        }else if (comprobarPartida(tablero,heroes) == 0){
                                            val response = Response(
                                                "${heroe.nombre} ha perdido prueba de ${prueba.nombre}",
                                                200)
                                            call.respond(response)
                                        }else{
                                            ConexionBD.actualizarEstadoTablero(tableroEntero.id,2)
                                            val response = Response(
                                                "Has perdido la partida",
                                                200)
                                            call.respond(response)
                                        }
                                    }
                                }
                                else if(heroe.capacidad == 0){
                                    pruebaSeleccionada.estado = 2
                                    ConexionBD.actualizarEstadoCasilla(pruebaSeleccionada.id, 2)
                                    if(comprobarPartida(tablero,heroes) == 1){
                                        tableroEntero.estado = 1
                                        ConexionBD.actualizarEstadoTablero(tableroEntero.id,1)
                                        ConexionBD.sumarVictoria(tableroEntero.id_jugador)
                                        val response = Response(
                                            "Has ganado la partida!!",
                                            200)
                                        call.respond(response)
                                    }else if (comprobarPartida(tablero,heroes) == 0){
                                        val response = Response(
                                            "${heroe.nombre} no tiene capacidad para realizar la prueba",
                                            200)
                                        call.respond(response)
                                    }else{
                                        ConexionBD.actualizarEstadoTablero(tableroEntero.id,2)
                                        val response = Response(
                                            "Has perdido la partida",
                                            200)
                                        call.respond(response)
                                    }
                                }
                            }
                        }
                    }
                }
            }else{
                if(tablero[cDestapada].estado != 0) {
                    val response = Response("Casilla ya destapada", 404)
                    call.respond(response)
                }else{
                    val response = Response("Casilla no encontrada", 404)
                    call.respond(response)
                }
            }
        }else{
            val response = Response("Tablero con partida terminada", 404)
            call.respond(response)
        }

    }

    //Si devuelve 0 la partida sigue, si devuelve 1 el jugador gana, si devuelve 2 el jugador pierde
    fun comprobarPartida(tablero: ArrayList<Casilla>, heroes: ArrayList<Heroe>): Int {
        var partidaTerminada = 0
        var contadorCasillasTerminadas = 0
        var contadorHeroesVivos = 0
        var mitadTablero = tablero.size/2

        for (casilla in tablero) {
            if (casilla.estado != 0) {
                contadorCasillasTerminadas++
            }
        }

        if (contadorCasillasTerminadas >= mitadTablero) {
            for (heroe in heroes) {
                if (heroe.capacidad > 0) {
                    contadorHeroesVivos++
                }
            }

            if(contadorHeroesVivos >=1){
                partidaTerminada = 1
                for (heroe in heroes) {
                    ConexionBD.actualizarCapacidadHeroe(heroe.id, 50)
                }
            }else{
                partidaTerminada = 2
                for (heroe in heroes) {
                    ConexionBD.actualizarCapacidadHeroe(heroe.id, 50)
                }
            }
        }

        return partidaTerminada
    }


    suspend fun mostrarTableros(call: ApplicationCall){
        val id_user = call.parameters["id"]?.toInt()
        val tableros = ConexionBD.obtenerTablerosJugador(id_user)
        call.respond(tableros)
    }

    suspend fun iniciarTablero(call: ApplicationCall) {
        val tableros = ConexionBD.obtenerTableros()
        val pruebas = ConexionBD.obtenerPruebas()
        val idUser = call.parameters["id"]
        var casillas = arrayListOf<Casilla>()
        val numerosPosibles = listOf(5, 10, 15, 20, 25, 30, 35, 40, 45, 50)
        var n = false

        // Obtener el último tablero del array tableros
        var ultimoTablero = tableros.lastOrNull()

        // Verificar si hay al menos un tablero
        while(n == false){
            if (ultimoTablero != null) {
                // Crear un array de 20 objetos Casillas con id_prueba aleatorio
                for (i in 1..20) {
                    val idPruebaAleatorio = pruebas.random().id
                    val capacidadRequeridaAleatoria = numerosPosibles.random()
                    val casilla = Casilla(
                        id = i,
                        id_prueba = idPruebaAleatorio,
                        id_tablero = ultimoTablero.id + 1,
                        capacidad_requerida = capacidadRequeridaAleatoria,
                        estado = 0 // Puedes ajustar el valor del estado según tus necesidades
                    )
                    casillas.add(casilla)
                }


                if (idUser != null) {
                    ConexionBD.insertarTablero(ultimoTablero.id+1,0,idUser)
                }
                ConexionBD.insertarCasillas(casillas)

                n = true
            }else{
                ultimoTablero = Tablero(0,0,0)
            }
        }

        call.respond(casillas)
    }


}