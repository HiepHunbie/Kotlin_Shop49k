package com.example.shop49k.model.userInfo

import android.os.Parcel
import android.os.Parcelable

data class City_data (

	val id : Int,
	val title : String,
	val code : Int,
	val type : String,
	val slug : String,
	val region : Int,
	val name_with_type : String,
	val latitude : Double,
	val longitude : Double,
	val order : Int,
	val thumbnail : String,
	val is_featured : Int
)