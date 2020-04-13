package com.example.shop49k.ui.shipperMain

import com.example.shop49k.base.BasePresenter
import com.example.shop49k.network.PostApi
import com.example.shop49k.ui.main.MainView
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ShipperMainPresenter (mainView: ShipperMainView) : BasePresenter<ShipperMainView>(mainView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}