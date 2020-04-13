package com.example.shop49k.model.image.updateImage

data class DeleteImageInput (
    val user_images : List<UserImagesDelete>
)
data class UserImagesDelete (
    val command : String,
    val images : List<Int>
)