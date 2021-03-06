package com.pushkin.authtest7.base

import com.pushkin.authtest7.security.exception.UserNotAuthorizedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.server.ResponseStatusException

open class BaseController {

    private val log = LoggerFactory.getLogger(this::class.java)

    protected fun <T> processServiceExceptions(block: () -> T) =
        try {
            ResponseEntity.ok<T?>(block())
        } catch (e: UserNotAuthorizedException) {
            log.error("$e")
            e.printStackTrace()
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "User not authorized to run", e)
        } catch (e: Exception) {
            log.error("$e")
            e.printStackTrace()
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred processing the request", e)
        }

}
