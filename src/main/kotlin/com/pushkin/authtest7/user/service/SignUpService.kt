package com.pushkin.authtest7.user.service

import com.pushkin.authtest7.security.service.TokenHelperService
import com.pushkin.authtest7.security.vo.JWTResponseVO
import com.pushkin.authtest7.user.exception.SignUpException
import com.pushkin.authtest7.user.vo.ConfirmCodeRq
import com.pushkin.authtest7.user.vo.SignUpRq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SignUpService(
    @Autowired val tokenHelperService: TokenHelperService
) {

    @Throws(SignUpException::class)
    fun signUp(confirmCodeRq: ConfirmCodeRq): JWTResponseVO =
        JWTResponseVO(tokenHelperService.createDefaultUserToken(confirmCodeRq.login))

    @Throws(SignUpException::class)
    fun register(signUpRq: SignUpRq) {
        if (signUpRq.login == "bad") {
            throw SignUpException("Wrong name: ${signUpRq.login}")
        }
    }

}
