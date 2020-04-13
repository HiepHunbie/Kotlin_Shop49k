package com.example.shop49k.ui.smallActivity.createOffer

import android.app.Dialog
import com.example.shop49k.base.BaseView
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.offer.createOffer.OfferCreateResult
import com.example.shop49k.model.offer.editOffer.OfferResult

interface CreateOfferView : BaseView {
    fun successCreateOffer(offerResult: OfferCreateResult, dialog : Dialog?)
    fun errorCreateOffer(offerResult: String)
    fun successAddNewAddress(offerResult: OfferResult, dialog : Dialog?)
    fun errorAddNewAddress(offerResult: String)
    fun getCitySuccess(dataCityDetail: List<DataCityDetail>)
    fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>)
    fun getCommueSuccess(dataCityDetail: List<DataCityDetail>)
}