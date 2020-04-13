package com.example.shop49k.model.offer.addAddress

data class Address (

	val city_id : Int,
	val city_name : String,
	val district_id : Int,
	val district_name : String,
	val commune_id : Int,
	val commune_name : String,
	val external_address : String,
	val offer_id : Int
)