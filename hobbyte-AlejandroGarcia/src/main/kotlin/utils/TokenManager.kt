package utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.Usuario

class TokenManager {

    var secret = Constantes.claveJWT


    fun generateJWTToken(user:Usuario):String{
        val token = JWT.create()
            .withClaim("uid", user.id)
            .sign(Algorithm.HMAC256(secret))

        return token
    }

    fun verifyJWTToken() : JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secret))
            .build()
    }

}