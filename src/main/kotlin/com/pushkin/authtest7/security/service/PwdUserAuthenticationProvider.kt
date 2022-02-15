package com.pushkin.authtest7.security.service

import com.pushkin.authtest7.security.token.PwdUserAuthenticationToken
import com.pushkin.authtest7.security.exception.UserNotAuthorizedException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component

@Component
class PwdUserAuthenticationProvider : AuthenticationProvider {

    @Throws(UserNotAuthorizedException::class)
    override fun authenticate(authentication: Authentication?): Authentication {
        val userAuthToken = authentication as PwdUserAuthenticationToken
        val login = userAuthToken.principal.toString()
        if (login != "bad") {
            return UsernamePasswordAuthenticationToken(
                login,
                null,
                listOf(GrantedAuthority { "ROLE_USER" })
            )
        }
        throw UserNotAuthorizedException("User $login is not allowed")
    }

    override fun supports(authentication: Class<*>?): Boolean =
        authentication == PwdUserAuthenticationToken::class.java
}
