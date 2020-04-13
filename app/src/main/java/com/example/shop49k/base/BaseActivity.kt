package com.example.shop49k.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.masterData.MasterData
import com.example.shop49k.model.userInfo.UserInfo
import com.example.shop49k.utils.DATA_USER_INFO
import com.example.shop49k.utils.MASTER_DATA
import com.google.gson.GsonBuilder
import java.io.Serializable

abstract class BaseActivity<P : com.example.shop49k.base.BasePresenter<com.example.shop49k.base.BaseView>> : com.example.shop49k.base.BaseView, AppCompatActivity() {
    lateinit var presenter: P
    var prefs: SharedPreferences? = null
    private val PREFS_FILENAME ="com.example.shop49k"
    var dataUserInfo: UserInfo? = null
    var masterData: MasterData? = null
    var gson = GsonBuilder().create()
    var listCity : List<DataCityDetail> = ArrayList<DataCityDetail>()
    var listDistrict : List<DataCityDetail> = ArrayList<DataCityDetail>()
    var listCommue : List<DataCityDetail> = ArrayList<DataCityDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= 16) {
//            getWindow().setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
//            getWindow().getDecorView().setSystemUiVisibility(3328);
//        }else{
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
        presenter = instantiatePresenter()
        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        dataUserInfo = gson.fromJson(prefs!!.getString(DATA_USER_INFO,"").toString(), UserInfo::class.java)
        masterData = gson.fromJson(prefs!!.getString(MASTER_DATA,"").toString(), MasterData::class.java)
    }

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P

    override fun getContext(): Context {
        return this
    }

}