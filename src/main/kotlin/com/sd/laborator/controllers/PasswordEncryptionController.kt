package com.sd.laborator.controllers

import com.sd.laborator.interfaces.IAuthService
import com.sd.laborator.pojo.MatchPasswordModel
import com.sd.laborator.utils.ControllerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PasswordEncryptionController {
    @Autowired
    private lateinit var _authService: IAuthService

    @RequestMapping(value = ["/encrypt"], method = [RequestMethod.GET])
    fun encryptPassword(@RequestParam() password: String): ResponseEntity<Any> {
        val response = _authService.encodePassword(password)
        return ControllerUtils.makeResponse(response)
    }
    @RequestMapping(value = ["/match"], method = [RequestMethod.POST])
    fun matchPassword(@RequestBody matchModel: MatchPasswordModel): ResponseEntity<Any>
    {
        val response = _authService.matchPassword(matchModel)
        return ControllerUtils.makeResponse(response)
    }
}