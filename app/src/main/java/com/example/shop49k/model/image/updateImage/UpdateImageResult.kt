package com.example.shop49k.model.image.updateImage

import com.example.shop49k.model.error.ErrorType

data class UpdateImageResult (
    val status : Int,
    val message : String,
    val data : List<DataUpdateImageResult>,
    val errors : ErrorType
)

data class DataUpdateImageResult(
    val id : String,
    val url : String,
    val name : String,
    val original_name : String,
    val user_id : String,
    val type : String
)