package com.example.shop49k.ui.fragment.sale

import android.content.Context
import com.example.shop49k.R
import com.example.shop49k.base.BasePresenter
import com.example.shop49k.network.PostApi
import com.example.shop49k.ui.fragment.home.HomeView
import com.example.shop49k.utils.`object`.ErrorMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class SalePresenter (postView: SaleView) : BasePresenter<SaleView>(postView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    fun getListSale(context: Context) {
        subscription = postApi
            .getListSale()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    view.getListSaleSuccess(postList.data)
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.getListSaleError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.getListSaleError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.getListSaleError(context.getString(R.string.disconnection))
                    } else {
                        view.getListSaleError(context.getString(R.string.certification_error))
                    }

                }
            )

    }
}