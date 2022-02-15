package com.pushkin.authtest7.security.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class JWTAuthorizationFilter(private val jwtDecoder: JWTDecoder, authenticationManager: AuthenticationManager?) :
    BasicAuthenticationFilter(authenticationManager) {

    companion object {
        private const val HEADER_STRING = "Authorization"
        private const val TOKEN_PREFIX = "Bearer "
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(HEADER_STRING)
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res)
            return
        }
        val authentication = getAuthentication(req)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, res)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {
            val decodedJWT = jwtDecoder.decode(token)
            val user = decodedJWT.subject
            val authorities = jwtDecoder.getAuthorities(decodedJWT)
            return if (user != null) {
                UsernamePasswordAuthenticationToken(user, null, authorities)
            } else null
        }
        return null
    }

}
