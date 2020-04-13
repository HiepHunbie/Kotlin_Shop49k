package com.example.shop49k.ui.fragment.profileShipper

import com.example.shop49k.base.BasePresenter
import com.example.shop49k.network.PostApi
import com.example.shop49k.ui.fragment.profile.ProfileView
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ProfileShipperPresenter (mainView: ProfileShipperView) : BasePresenter<ProfileShipperView>(mainView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}