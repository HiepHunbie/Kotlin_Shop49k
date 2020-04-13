package com.example.shop49k.ui.forgotPassword

import com.example.shop49k.base.BaseView
import com.example.shop49k.model.forgotPassword.ForgotResult
import com.example.shop49k.model.offer.DataOfferDetail

interface ForgotPassWordView : BaseView {

    fun forgotPasswordSuccess(forgotResult: ForgotResult)

    fun forgotPasswordError(forgotResult: String)

    fun forgotPasswordCompleteSuccess(forgotResult: ForgotResult)
}