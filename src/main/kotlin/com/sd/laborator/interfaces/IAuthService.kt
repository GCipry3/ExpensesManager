package com.sd.laborator.interfaces

import com.sd.laborator.pojo.MatchPasswordModel
import com.sd.laborator.pojo.MyCustomResponse

interface IAuthService {
    fun encodePassword(password: String):MyCustomResponse<Any>
    fun matchPassword(matchModel: MatchPasswordModel): MyCustomResponse<Any>
}