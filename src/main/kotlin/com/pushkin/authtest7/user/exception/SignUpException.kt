package com.pushkin.authtest7.user.exception

import com.pushkin.authtest7.security.exception.UserNotAuthorizedException

class SignUpException : UserNotAuthorizedException {
    constructor(msg: String?, cause: Throwable?) : super(msg, cause)
    constructor(msg: String?) : super(msg)
}
