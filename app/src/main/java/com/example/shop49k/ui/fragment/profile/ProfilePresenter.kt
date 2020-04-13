package com.example.shop49k.ui.fragment.profile

import android.app.Dialog
import android.content.Context
import com.example.shop49k.R
import com.example.shop49k.base.BasePresenter
import com.example.shop49k.model.changePassword.ChangePasswordInput
import com.example.shop49k.model.personInfo.AvatarInput
import com.example.shop49k.model.personInfo.PersonInfoInput
import com.example.shop49k.network.PostApi
import com.example.shop49k.ui.login.LoginView
import com.example.shop49k.utils.`object`.CommonUtil
import com.example.shop49k.utils.`object`.ErrorMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ProfilePresenter (mainView: ProfileView) : BasePresenter<ProfileView>(mainView) {
    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
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
                        view.errorGetUserInfo(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.errorGetUserInfo(context.getString(R.string.disconnection))
                    } else {
                        view.errorGetUserInfo(context.getString(R.string.certification_error))
                    }

                }
            )

    }

    fun uploadImage(context: Context, token: String, file: MultipartBody.Part, requestBodyUpload: RequestBody) {
        subscription = postApi
            .uploadImage(token,file)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.uploadSuccess(postList)
                    }else{
                        view.uploadError(CommonUtil.convertErrorsToText(postList.errors!!))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.uploadError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.uploadError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.uploadError(context.getString(R.string.disconnection))
                    } else {
                        view.uploadError(context.getString(R.string.certification_error))
                    }

                }
            )

    }

    fun updateAvatar(context: Context,token: String, personInfoInput: AvatarInput) {
        subscription = postApi
            .updateAvatar(token,personInfoInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.updateUserSuccess(postList)
                    }else{
                        view.updateUserError(CommonUtil.convertErrorsToText(postList.errors))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.updateUserError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.updateUserError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.updateUserError(context.getString(R.string.disconnection))
                    } else {
                        view.uploadError(context.getString(R.string.certification_error))
                    }

                }
            )

    }
    fun checkOldPassword(context: Context,token: String, personInfoInput: ChangePasswordInput, dialog:Dialog?) {
        subscription = postApi
            .changePassword(token,personInfoInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.checkOldPasswordSuccess(postList,dialog!!)
                    }else{
                        view.checkOldPasswordError(CommonUtil.convertErrorsToText(postList.errors))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.checkOldPasswordError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.checkOldPasswordError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.checkOldPasswordError(context.getString(R.string.disconnection))
                    } else {
                        view.checkOldPasswordError(context.getString(R.string.certification_error))
                    }

                }
            )

    }
    fun changePassword(context: Context,token: String, personInfoInput: ChangePasswordInput, dialog:Dialog?) {
        subscription = postApi
            .changePassword(token,personInfoInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.changePasswordSuccess(postList,dialog!!)
                    }else{
                        view.changePasswordError(CommonUtil.convertErrorsToText(postList.errors))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.changePasswordError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.changePasswordError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.changePasswordError(context.getString(R.string.disconnection))
                    } else {
                        view.changePasswordError(context.getString(R.string.certification_error))
                    }

                }
            )

    }
}