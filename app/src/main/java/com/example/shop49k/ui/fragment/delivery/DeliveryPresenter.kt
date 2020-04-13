package com.example.shop49k.ui.fragment.delivery

import com.example.shop49k.base.BasePresenter
import com.example.shop49k.network.PostApi
import com.example.shop49k.ui.fragment.order.OrderView
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class DeliveryPresenter (postView: DeliveryView) : BasePresenter<DeliveryView>(postView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}