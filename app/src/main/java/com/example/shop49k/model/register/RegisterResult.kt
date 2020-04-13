package com.example.shop49k.model.register

import com.example.shop49k.model.error.ErrorType
import com.google.gson.JsonObject
import org.json.JSONObject

data class RegisterResult (
    val status : Int,
    val message : String,
    val errors: ErrorType
)