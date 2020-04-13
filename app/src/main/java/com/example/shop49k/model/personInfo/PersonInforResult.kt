package com.example.shop49k.model.personInfo

import com.example.shop49k.model.error.ErrorType
import com.google.gson.JsonObject

class PersonInforResult (
    val status : Int,
    val message : String,
    val errors: ErrorType
)