package com.example.shop49k.ui.fragment.sale

import com.example.shop49k.base.BaseView
import com.example.shop49k.base.IBasePresenter
import com.example.shop49k.model.sale.DataSaleDetail
import com.example.shop49k.model.userInfo.UserInfo

interface SaleView : BaseView {
    interface Presenter : IBasePresenter {
        fun getAlertDetail(offset: Int = 0)
    }

    fun getListSaleSuccess(dataSaleDetail: List<DataSaleDetail>)
    fun getListSaleError(mess: String)
}