package com.example.shop49k.ui.fragment.home

import com.example.shop49k.base.BaseView
import com.example.shop49k.base.IBasePresenter

interface HomeView : BaseView {
    interface Presenter : IBasePresenter {
        fun getAlertDetail(offset: Int = 0)
    }
}