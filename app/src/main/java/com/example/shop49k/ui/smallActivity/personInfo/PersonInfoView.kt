package com.example.shop49k.ui.smallActivity.personInfo

import com.example.shop49k.base.BaseView
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.image.uploadImage.ImageUploadResult
import com.example.shop49k.model.personInfo.PersonInforResult

interface PersonInfoView : BaseView {

    fun getCitySuccess(dataCityDetail: List<DataCityDetail>)
    fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>)
    fun getCommueSuccess(dataCityDetail: List<DataCityDetail>)
    fun uploadSuccess(forgotResult: ImageUploadResult)
    fun uploadError(forgotResult: String)
    fun updateUserSuccess(forgotResult: PersonInforResult)
    fun updateUserError(forgotResult: String)
}