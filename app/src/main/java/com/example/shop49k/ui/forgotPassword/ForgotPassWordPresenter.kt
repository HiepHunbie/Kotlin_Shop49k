package com.example.shop49k.ui.forgotPassword

import android.content.Context
import com.example.shop49k.R
import com.example.shop49k.base.BasePresenter
import com.example.shop49k.model.forgotPassword.ForgotCompleteInput
import com.example.shop49k.model.forgotPassword.ForgotInput
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

class ForgotPassWordPresenter (mainView: ForgotPassWordView) : BasePresenter<ForgotPassWordView>(mainView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    fun forgotPassword(context: Context, forgotInput: ForgotInput) {
        subscription = postApi
            .forgotPassword(forgotInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.forgotPasswordSuccess(postList)
                    }else if(postList.status == 500){
                        view.forgotPasswordError(postList.message!!)
                    }else{
                        view.forgotPasswordError(CommonUtil.convertErrorsToText(postList.errors!!))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.forgotPasswordError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.forgotPasswordError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.forgotPasswordError(context.getString(R.string.disconnection))
                    } else {
                        view.forgotPasswordError(context.getString(R.string.certification_error))
                    }

                }
            )

    }

    fun forgotPasswordComplete(context: Context, forgotInput: ForgotCompleteInput) {
        subscription = postApi
            .forgotPasswordComplete(forgotInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.forgotPasswordCompleteSuccess(postList)
                    }else{
                        view.forgotPasswordError(CommonUtil.convertErrorsToText(postList.errors!!))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.forgotPasswordError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.forgotPasswordError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.forgotPasswordError(context.getString(R.string.disconnection))
                    } else {
                        view.forgotPasswordError(context.getString(R.string.certification_error))
                    }

                }
            )

    }
}