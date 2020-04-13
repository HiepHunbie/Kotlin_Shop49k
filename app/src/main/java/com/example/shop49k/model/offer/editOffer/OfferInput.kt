package com.example.shop49k.model.offer.editOffer

data class OfferInput (
    val id : Int,
    val status : Int,
    val start_time : String,
    val end_time : String,
    val how_to_use : String,
    val apply_condition : String,
    val title : String,
    val offer_number : Int,
    val offer_left : Int,
    val discount_type : Int,
    val discount_value : String,
    val discount_note : String,
    val type : Int
)