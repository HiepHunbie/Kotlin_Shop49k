package com.example.shop49k.model.forgotPassword

import com.example.shop49k.model.error.ErrorType
import com.google.gson.JsonObject

data class ForgotResult (
    val status : Int,
    val message : String?,
    val errors: ErrorType
)