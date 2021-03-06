package com.example.shop49k.ui.fragment.home

import com.example.shop49k.base.BasePresenter
import com.example.shop49k.network.PostApi
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomePresenter (postView: HomeView) : BasePresenter<HomeView>(postView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}