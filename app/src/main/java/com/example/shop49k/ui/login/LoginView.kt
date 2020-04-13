package com.example.shop49k.ui.login

import com.example.shop49k.base.BaseView
import com.example.shop49k.model.error.ErrorType
import com.example.shop49k.model.login.LoginData
import com.example.shop49k.model.masterData.MasterData
import com.example.shop49k.model.masterData.MasterDataData
import com.example.shop49k.model.userInfo.UserInfo
import com.example.shop49k.model.userInfo.UserInfoData

interface LoginView : BaseView {
    fun updateLogin(login: LoginData)
    fun showErrorLogin(mess: String)
    fun getUserInfoSuccess(userInfo: UserInfo)
    fun errorGetUserInfo(mess: String)
    fun getMasterDataSuccess(masterDataData: MasterData)
    fun errorGetMasterData(mess: String)
}