package com.example.shop49k.model.login

import com.example.shop49k.model.error.ErrorType

data class LoginData(val status: Int, val token: String, val common_error:String, val errors: ErrorType)