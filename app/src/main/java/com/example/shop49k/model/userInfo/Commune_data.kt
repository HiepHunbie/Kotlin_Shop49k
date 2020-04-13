package com.example.shop49k.model.userInfo

import android.os.Parcel
import android.os.Parcelable


data class Commune_data (

	val id : Int,
	val district_id : String,
	val title : String,
	val code : Int,
	val type : String,
	val slug : String,
	val name_with_type : String,
	val path : String,
	val path_with_type : String,
	val latitude : String,
	val longitude : String,
	val order : Int,
	val is_featured : Int
)