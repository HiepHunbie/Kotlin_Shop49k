package com.example.shop49k.ui.fragment.offer

import android.content.Context
import com.example.shop49k.R
import com.example.shop49k.base.BasePresenter
import com.example.shop49k.network.PostApi
import com.example.shop49k.utils.`object`.ErrorMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class OfferPresenter (postView: OfferView) : BasePresenter<OfferView>(postView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
    fun getOffers(context: Context,token:String,sort_field : String,sort_order:String
                  ,status:String,limit : String,page:String, isLoadMore : Boolean) {
        subscription = postApi
            .getOffers(token,sort_field,sort_order,status,limit,page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    view.getOffersDataSuccess(postList.data,isLoadMore)
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.errorGetOffers(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.errorGetOffers(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.errorGetOffers(context.getString(R.string.disconnection))
                    } else {
                        view.errorGetOffers(context.getString(R.string.certification_error))
                    }

                }
            )

    }
    fun getOffersWithId(context: Context,token:String,sort_field : String,sort_order:String, id : String) {
        subscription = postApi
            .getOffersWithId(token,sort_field,id,sort_order)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    view.getOffersWithIdDataSuccess(postList.data)
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.errorGetOffers(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.errorGetOffers(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.errorGetOffers(context.getString(R.string.disconnection))
                    } else {
                        view.errorGetOffers(context.getString(R.string.certification_error))
                    }

                }
            )

    }

}