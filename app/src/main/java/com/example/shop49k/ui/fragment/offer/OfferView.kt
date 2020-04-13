package com.example.shop49k.ui.fragment.offer

import com.example.shop49k.base.BaseView
import com.example.shop49k.base.IBasePresenter
import com.example.shop49k.model.login.LoginData
import com.example.shop49k.model.offer.DataOfferDetail

interface OfferView : BaseView {
    interface Presenter : IBasePresenter {
        fun getAlertDetail(offset: Int = 0)
    }

    fun getOffersDataSuccess(dataOfferDetail: List<DataOfferDetail>, isLoadMore : Boolean)
    fun errorGetOffers(dataOfferDetail: String)
    fun getOffersWithIdDataSuccess(dataOfferDetail: DataOfferDetail)
}