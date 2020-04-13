package com.example.shop49k.ui.smallActivity.ImageDetailProfile

import com.example.shop49k.base.BaseView
import com.example.shop49k.model.forgotPassword.ForgotResult
import com.example.shop49k.model.image.updateImage.UpdateImageResult
import com.example.shop49k.model.image.uploadImage.ImageUploadResult
import com.example.shop49k.model.personInfo.PersonInforResult

interface ImageDetailProfileView : BaseView {
    fun uploadSuccess(forgotResult: ImageUploadResult)
    fun uploadError(forgotResult: String)
    fun addImageSuccess(forgotResult: UpdateImageResult)
    fun addImageError(forgotResult: String)
    fun deleteImageSuccess(forgotResult: UpdateImageResult)
    fun deleteImageError(forgotResult: String)
}