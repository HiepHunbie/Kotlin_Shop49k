package com.example.shop49k.model.changePassword

import com.example.shop49k.model.error.ErrorType
import com.google.gson.JsonObject

data class ChangePasswordResult (
    val status : Int,
    val errors: ErrorType,
    val message : String
)