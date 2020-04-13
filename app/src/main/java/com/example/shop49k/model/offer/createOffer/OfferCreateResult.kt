package com.example.shop49k.model.offer.createOffer

import com.example.shop49k.model.error.ErrorType
import com.google.gson.JsonObject

data class OfferCreateResult (
    val status:Int?,
    val message:String?,
    val offer_id:Int?,
    val errors: ErrorType?
)