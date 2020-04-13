package com.example.shop49k.ui.splash

import com.example.shop49k.base.BasePresenter
import com.example.shop49k.network.PostApi
import com.example.shop49k.ui.login.LoginView
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SplashPresenter (mainView: SplashView) : BasePresenter<SplashView>(mainView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}