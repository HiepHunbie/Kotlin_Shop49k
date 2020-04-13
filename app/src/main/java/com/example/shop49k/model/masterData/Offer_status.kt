package com.example.shop49k.model.masterData

import com.squareup.moshi.Json

data class Offer_status (

	@Json(name = "0") val level0 : String,
	@Json(name = "1") val level1 : String,
	@Json(name = "2") val level2 : String,
	@Json(name = "3") val level3 : String
)