package com.example.shop49k.ui.fragment.profile

import android.app.Dialog
import com.example.shop49k.base.BaseView
import com.example.shop49k.model.changePassword.ChangePasswordResult
import com.example.shop49k.model.image.uploadImage.ImageUploadResult
import com.example.shop49k.model.personInfo.PersonInforResult
import com.example.shop49k.model.userInfo.UserInfo

interface ProfileView : BaseView {
    fun getUserInfoSuccess(userInfo: UserInfo)
    fun errorGetUserInfo(mess: String)
    fun uploadSuccess(forgotResult: ImageUploadResult)
    fun uploadError(forgotResult: String)
    fun updateUserSuccess(forgotResult: PersonInforResult)
    fun updateUserError(forgotResult: String)
    fun changePasswordSuccess(forgotResult: ChangePasswordResult, dialog:Dialog)
    fun changePasswordError(forgotResult: String)
    fun checkOldPasswordSuccess(forgotResult: ChangePasswordResult, dialog:Dialog)
    fun checkOldPasswordError(forgotResult: String)
}