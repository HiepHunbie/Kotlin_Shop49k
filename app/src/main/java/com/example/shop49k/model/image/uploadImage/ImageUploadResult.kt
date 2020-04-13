package com.example.shop49k.model.image.uploadImage

import com.example.shop49k.model.error.ErrorType

data class ImageUploadResult (
    val status : Int,
    val data : List<DataImage>,
val errors:ErrorType
)

data class DataImage(
    val name : String,
    val url : String
)