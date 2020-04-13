package com.example.shop49k.model.userInfo

data class District_data (

	val id : Int,
	val city_id : String,
	val title : String,
	val code : Int,
	val type : String,
	val slug : String,
	val name_with_type : String,
	val path : String,
	val path_with_type : String,
	val latitude : Double,
	val longitude : Double,
	val order : String,
	val is_featured : Int
)