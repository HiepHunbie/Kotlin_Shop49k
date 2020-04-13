package com.example.shop49k.ui.signup

import android.content.Context
import com.example.shop49k.R
import com.example.shop49k.base.BasePresenter
import com.example.shop49k.model.register.RegisterInput
import com.example.shop49k.model.userInfo.UserInfo
import com.example.shop49k.network.PostApi
import com.example.shop49k.utils.DATA_USER_INFO
import com.example.shop49k.utils.`object`.CommonUtil
import com.example.shop49k.utils.`object`.ErrorMessage
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class SignUpPresenter (mainView: SignUpView) : BasePresenter<SignUpView>(mainView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    // 1: validate in account screen ,  2 : validate in user screen
    fun validateUser(context: Context, registerInput: RegisterInput, type: Int) {
        subscription = postApi
            .validateUser(registerInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        if(type == 1){
                            view.validateUserAcountSuccess(postList)
                        }else if(type == 2){
                            view.validateUserUserSuccess(postList)
                        }
                    }else{
                        view.validateUserError(CommonUtil.convertErrorsToText(postList.errors!!))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.registerError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.registerError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.registerError(context.getString(R.string.disconnection))
                    } else {
                        view.registerError(context.getString(R.string.certification_error))
                    }

                }
            )

    }

    fun register(context: Context, registerInput: RegisterInput) {
        subscription = postApi
            .register(registerInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.registerSuccess(postList)
                    }else{
                        view.registerError(CommonUtil.convertErrorsToText(postList.errors!!))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.registerError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.registerError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.registerError(context.getString(R.string.disconnection))
                    } else {
                        view.registerError(context.getString(R.string.certification_error))
                    }

                }
            )

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