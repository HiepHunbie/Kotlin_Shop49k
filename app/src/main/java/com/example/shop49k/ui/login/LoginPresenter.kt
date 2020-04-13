package com.example.shop49k.ui.login

import android.content.Context
import com.example.shop49k.R
import com.example.shop49k.base.BasePresenter
import com.example.shop49k.model.login.LoginInput
import com.example.shop49k.network.PostApi
import com.example.shop49k.utils.`object`.CommonUtil
import com.example.shop49k.utils.`object`.ErrorMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class LoginPresenter (mainView: LoginView) : BasePresenter<LoginView>(mainView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    fun login(context: Context, loginInput: LoginInput) {
        subscription = postApi
            .login(loginInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.updateLogin(postList)
                    }else if(postList.status == 500){
                        view.showErrorLogin(postList.common_error)
                    }else if(postList.status == 400){
                        view.showErrorLogin(CommonUtil.convertErrorsToText(postList.errors!!))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.showErrorLogin(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.showErrorLogin(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.showErrorLogin(context.getString(R.string.disconnection))
                    } else {
                        view.showErrorLogin(context.getString(R.string.certification_error))
                    }

                }
            )

    }

    fun getUserInfo(context: Context, token: String) {
        subscription = postApi
            .getUserInfo(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    view.getUserInfoSuccess(postList)
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.errorGetUserInfo(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.showErrorLogin(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.showErrorLogin(context.getString(R.string.disconnection))
                    } else {
                        view.showErrorLogin(context.getString(R.string.certification_error))
                    }

                }
            )

    }

    fun getMasterData(context: Context) {
        subscription = postApi
            .getMasterData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    view.getMasterDataSuccess(postList)
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.errorGetMasterData(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.showErrorLogin(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.showErrorLogin(context.getString(R.string.disconnection))
                    } else {
                        view.showErrorLogin(context.getString(R.string.certification_error))
                    }

                }
            )

    }
}