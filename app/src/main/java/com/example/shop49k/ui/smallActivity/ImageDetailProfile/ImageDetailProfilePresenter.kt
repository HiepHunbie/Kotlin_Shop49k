package com.example.shop49k.ui.smallActivity.ImageDetailProfile

import android.content.Context
import com.example.shop49k.R
import com.example.shop49k.base.BasePresenter
import com.example.shop49k.model.image.updateImage.DeleteImageInput
import com.example.shop49k.model.image.updateImage.UpdateImageInput
import com.example.shop49k.model.personInfo.PersonInfoInput
import com.example.shop49k.network.PostApi
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

class ImageDetailProfilePresenter (postView: ImageDetailProfileView) : BasePresenter<ImageDetailProfileView>(postView){

    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    fun uploadImage(context: Context,token: String, file: MultipartBody.Part,requestBodyUpload: RequestBody) {
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
    fun addImage(context: Context,token: String, updateImageInput: UpdateImageInput) {
        subscription = postApi
            .updateImage(token,updateImageInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.addImageSuccess(postList)
                    }else{
                        view.addImageError(CommonUtil.convertErrorsToText(postList.errors))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.addImageError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.addImageError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.addImageError(context.getString(R.string.disconnection))
                    } else {
                        view.addImageError(context.getString(R.string.certification_error))
                    }

                }
            )

    }
    fun deleteImage(context: Context,token: String, deleteImageInput: DeleteImageInput) {
        subscription = postApi
            .deleteImage(token,deleteImageInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.deleteImageSuccess(postList)
                    }else{
                        view.deleteImageError(CommonUtil.convertErrorsToText(postList.errors))
                    }
                },
                {
                        error ->
                    if (error is HttpException) {
                        val responseBody : ResponseBody = error.response().errorBody()!!
                        view.deleteImageError(ErrorMessage.getErrorMessage(responseBody,context))
                    } else if (error is SocketTimeoutException) {
                        view.deleteImageError(context.getString(R.string.time_out))
                    } else if (error is IOException) {
                        view.deleteImageError(context.getString(R.string.disconnection))
                    } else {
                        view.deleteImageError(context.getString(R.string.certification_error))
                    }

                }
            )

    }

}