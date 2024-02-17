package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import utils.Constantes
import utils.TokenManager



fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val tokenManager = TokenManager()

    install(Authentication) {
        jwt {
            verifier(tokenManager.verifyJWTToken())
            realm = Constantes.realm
            validate {
                val idClaim = it.payload.getClaim("uid")
                if (idClaim != null && idClaim.asInt() != null) {
                    JWTPrincipal(it.payload)
                } else {
                    null
                }
            }
        }
    }

    configureSerialization()
    configureRouting()

}
