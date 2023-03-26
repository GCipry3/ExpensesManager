package com.sd.laborator.utils

import com.sd.laborator.pojo.MyCustomError
import com.sd.laborator.pojo.MyCustomResponse
import org.springframework.http.ResponseEntity

object ControllerUtils {
    fun makeResponse(response: MyCustomResponse<Any>): ResponseEntity<Any> {
        return if (response.successfulOperation) {
            ResponseEntity.status(response.code).body(response.data)
        }else{
            val err = MyCustomError(response.code, response.error, response.message)
            ResponseEntity.status(response.code).body(err)
        }
    }
}