package com.example.shop49k.model.offer.editOffer

import com.example.shop49k.model.error.ErrorType
import com.google.gson.JsonObject

data class OfferResult (
    val status:Int,
    val message:String?,
    val errors: ErrorType
)