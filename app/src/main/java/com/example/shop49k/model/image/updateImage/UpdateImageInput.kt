package com.example.shop49k.model.image.updateImage

data class UpdateImageInput (
    var user_images : List<UserImages>
)

data class UserImages (
    val command : String,
    val images : List<Images>
)
data class Images(
    val name : String,
    val url : String
)