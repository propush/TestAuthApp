package com.pushkin.authtest7.security.service

import com.auth0.jwt.JWT
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenHelperService(val jwtDecoder: JWTDecoder) {

    companion object {
        private const val DEFAULT_TOKEN_TTL_SEC = 600
    }

    fun createToken(name: String, authorities: Collection<GrantedAuthority>, ttlSec: Int): String {
        return jwtDecoder.sign(
            JWT.create()
                .withSubject(name)
                .withExpiresAt(getTokenExpirationDate(ttlSec))
                .withClaim("scopes", authorities.joinToString(",") { it.authority })
        )
    }

    fun createDefaultUserToken(name: String) =
        createToken(name, listOf(GrantedAuthority { "ROLE_USER" }), DEFAULT_TOKEN_TTL_SEC)

    private fun getTokenExpirationDate(ttlSec: Int) =
        Date(System.currentTimeMillis() + ttlSec * 1000)

}
