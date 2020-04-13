package com.example.shop49k.ui.signup

import com.example.shop49k.base.BaseView
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.forgotPassword.ForgotResult
import com.example.shop49k.model.register.RegisterResult

interface SignUpView : BaseView {
    fun registerSuccess(registerResult: RegisterResult)
    fun registerError(registerResult: String)
    fun validateUserAcountSuccess(registerResult: RegisterResult)
    fun validateUserUserSuccess(registerResult: RegisterResult)
    fun validateUserError(registerResult: String)
    fun getCitySuccess(dataCityDetail: List<DataCityDetail>)
    fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>)
    fun getCommueSuccess(dataCityDetail: List<DataCityDetail>)
}