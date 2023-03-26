package com.sd.laborator.services

import com.sd.laborator.interfaces.IAuthService
import com.sd.laborator.pojo.MatchPasswordModel
import com.sd.laborator.pojo.MyCustomResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService : IAuthService {
    @Autowired
    private lateinit var _delegatingPasswordEncoder: PasswordEncoder

    private val passwordEncoder = MessageDigestPasswordEncoder("SHA-256")

    override fun encodePassword(password: String): MyCustomResponse<Any> {
        try{

            return MyCustomResponse(
                successfulOperation = true,
                code = 200,
                data = passwordEncoder.encode(password),
                message = "Password encoded successfully!"
            )
        } catch (e: Exception){
            return MyCustomResponse(
                successfulOperation = false,
                code = 500,
                data = Unit,
                error = e.message,
                message = "Password encoding failed!"
            )
        }
    }

    override fun matchPassword(matchModel: MatchPasswordModel): MyCustomResponse<Any> {
        return try{
            val currentHashPasswordWithAlg = "{SHA-256}${matchModel.hashPassword}"
            MyCustomResponse(
                successfulOperation = true,
                code = 200,
                data = "${_delegatingPasswordEncoder.matches(matchModel.password, currentHashPasswordWithAlg)}"
            )
        } catch (e: Exception){
            MyCustomResponse(
                successfulOperation = false,
                code = 500,
                data = Unit,
                error = e.message,
                message = "Password matching failed!"
            )
        }
    }

}