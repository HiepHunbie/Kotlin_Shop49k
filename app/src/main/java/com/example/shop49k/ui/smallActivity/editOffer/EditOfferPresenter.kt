package com.example.shop49k.ui.smallActivity.editOffer

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.example.shop49k.R
import com.example.shop49k.base.BasePresenter
import com.example.shop49k.model.offer.addAddress.AddAddressInput
import com.example.shop49k.model.offer.editOffer.OfferInput
import com.example.shop49k.network.PostApi
import com.example.shop49k.utils.`object`.CommonUtil
import com.example.shop49k.utils.`object`.ErrorMessage
import com.example.shop49k.utils.`object`.Dialogs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class EditOfferPresenter (postView: EditOfferView) : BasePresenter<EditOfferView>(postView){

    @Inject
    lateinit var postApi: PostApi

    private var subscription: Disposable? = null

    fun editOffer(token: String, offerInput: OfferInput, progressBar : ProgressBar, parentActivity: Activity, dialog : Dialog?) {

        progressBar.visibility = View.VISIBLE
        subscription = postApi
            .editOffer(token,offerInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { progressBar.visibility = View.GONE }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.successEditOffer(postList, dialog)
                    }else{
                        view.errorEditOffer(CommonUtil.convertErrorsToText(postList.errors!!))
                    }
                    progressBar.visibility = View.GONE},
                {
                        error ->
                    progressBar.visibility = View.GONE
                    if (error is HttpException) {
                        val responseBody : ResponseBody = (error as HttpException).response().errorBody()!!
                        view.errorEditOffer(ErrorMessage.getErrorMessage(responseBody,parentActivity))
                    } else if (error is SocketTimeoutException) {
                        Dialogs.showToastErrorNewNotListenerClick(parentActivity!!,parentActivity!!.getString(R.string.time_out))
//                            view.onTimeout()
                    } else if (error is IOException) {
                        Dialogs.showToastErrorNewNotListenerClick(parentActivity!!,parentActivity!!.getString(R.string.time_out))
//                            view.onNetworkError()
                    } else {
//                            view.onUnknownError(e.getMessage())
                    }

                }
            )

    }

    fun addNewAddress(token: String, addAddressInput:AddAddressInput, progressBar : ProgressBar, parentActivity: Activity, dialog : Dialog?) {

        progressBar.visibility = View.VISIBLE
        subscription = postApi
            .addAddress(token,addAddressInput)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate { progressBar.visibility = View.GONE }
            .subscribe(
                {postList ->
                    if(postList.status == 200){
                        view.successAddNewAddress(postList, dialog)
                    }else{
                        view.errorAddNewAddress(CommonUtil.convertErrorsToText(postList.errors!!))
                    }
                    progressBar.visibility = View.GONE},
                {
                        error ->
                    progressBar.visibility = View.GONE
                    if (error is HttpException) {
                        val responseBody : ResponseBody = (error as HttpException).response().errorBody()!!
                        view.errorAddNewAddress(ErrorMessage.getErrorMessage(responseBody,parentActivity))
                    } else if (error is SocketTimeoutException) {
                        Dialogs.showToastErrorNewNotListenerClick(parentActivity!!,parentActivity!!.getString(R.string.time_out))
//                            view.onTimeout()
                    } else if (error is IOException) {
                        Dialogs.showToastErrorNewNotListenerClick(parentActivity!!,parentActivity!!.getString(R.string.time_out))
//                            view.onNetworkError()
                    } else {
//                            view.onUnknownError(e.getMessage())
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