package com.example.shop49k.ui.main

import com.example.shop49k.base.BaseView
import com.example.shop49k.model.city.DataCityDetail

interface MainView : BaseView {

    fun addFragment(pos:Int)
    fun getCitySuccess(dataCityDetail: List<DataCityDetail>)
    fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>)
    fun getCommueSuccess(dataCityDetail: List<DataCityDetail>)
}