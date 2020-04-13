package com.example.shop49k.ui.smallActivity.qrCode

import com.example.shop49k.base.BasePresenter
import com.example.shop49k.network.PostApi
import com.example.shop49k.ui.smallActivity.personInfo.PersonInfoView
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class QrCodePresenter (postView: QrCodeView) : BasePresenter<QrCodeView>(postView){

    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null
}