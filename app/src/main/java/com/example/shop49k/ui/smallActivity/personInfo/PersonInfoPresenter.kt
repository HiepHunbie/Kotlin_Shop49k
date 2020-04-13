package com.example.shop49k.ui.smallActivity.personInfo

import android.content.Context
import com.example.shop49k.R
import com.example.shop49k.base.BasePresenter
import com.example.shop49k.model.personInfo.PersonInfoInput
import com.example.shop49k.network.PostApi
import com.example.shop49k.ui.smallActivity.ImageDetailProfile.ImageDetailProfileView
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

class PersonInfoPresenter (postView: PersonInfoView) : BasePresenter<PersonInfoView>(postView){

    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

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
                        view.uploadError(CommonUtil.convertErrorsToText(postList.errors))
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

    fun updatePersonInfo(context: Context,token: String, personInfoInput: PersonInfoInput) {
        subscription = postApi
            .updatePersonInfo(token,personInfoInput)
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
}