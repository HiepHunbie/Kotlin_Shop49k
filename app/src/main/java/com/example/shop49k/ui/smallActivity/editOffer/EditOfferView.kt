package com.example.shop49k.ui.smallActivity.editOffer

import android.app.Dialog
import com.example.shop49k.base.BaseView
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.offer.editOffer.OfferResult

interface EditOfferView : BaseView {

    fun successEditOffer(offerResult: OfferResult, dialog : Dialog?)
    fun errorEditOffer(offerResult: String)
    fun successAddNewAddress(offerResult: OfferResult, dialog : Dialog?)
    fun errorAddNewAddress(offerResult: String)
    fun getCitySuccess(dataCityDetail: List<DataCityDetail>)
    fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>)
    fun getCommueSuccess(dataCityDetail: List<DataCityDetail>)
}