package com.example.shop49k.ui.main

import android.content.Context
import com.example.shop49k.R
import com.example.shop49k.base.BasePresenter
import com.example.shop49k.network.PostApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class MainPresenter (mainView: MainView) : BasePresenter<MainView>(mainView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }


    fun getCity(context: Context, type: String, q:String) {
        subscription = postApi
            .getCity(type,q)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    view.getCitySuccess(postList.data)
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                    } else if (error is SocketTimeoutException) {
                    } else if (error is IOException) {
                    } else {
                    }

                }
            )

    }
    fun getDistrict(context: Context, type: String, q:String, city_id:String) {
        subscription = postApi
            .getDistrict(type,q,city_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    view.getDistrictSuccess(postList.data)
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                    } else if (error is SocketTimeoutException) {
                    } else if (error is IOException) {
                    } else {
                    }

                }
            )

    }
    fun getCommune(context: Context, type: String, q:String, district_id:String) {
        subscription = postApi
            .getCommune(type,q,district_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    view.getCommueSuccess(postList.data)
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                    } else if (error is SocketTimeoutException) {
                    } else if (error is IOException) {
                    } else {
                    }

                }
            )

    }
}