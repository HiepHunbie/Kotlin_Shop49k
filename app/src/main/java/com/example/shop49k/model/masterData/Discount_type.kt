package com.example.shop49k.model.masterData

import com.squareup.moshi.Json

data class Discount_type (

	@Json(name = "1") val level1 : String,
	@Json(name = "2") val level2 : String
)