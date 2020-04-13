package com.example.shop49k.ui.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.ui.fragment.home.HomeFragment
import com.example.shop49k.ui.fragment.offer.OfferFragment
import com.example.shop49k.ui.fragment.order.OrderFragment
import com.example.shop49k.ui.fragment.profile.ProfileFragment
import com.example.shop49k.ui.fragment.sale.SaleFragment
import com.example.shop49k.ui.smallActivity.createOffer.CreateOfferActivity
import com.example.shop49k.utils.*
import com.example.shop49k.utils.`object`.CommonUtil
import com.example.shop49k.utils.`object`.Dialogs
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.Serializable

class MainActivity : BaseActivity<MainPresenter>(),MainView, Serializable {
    override fun getCitySuccess(dataCityDetail: List<DataCityDetail>) {
        listCity = dataCityDetail
    }

    override fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>) {
        listDistrict = dataCityDetail
    }

    override fun getCommueSuccess(dataCityDetail: List<DataCityDetail>) {
        listCommue = dataCityDetail
    }
    override fun instantiatePresenter(): MainPresenter {
        return MainPresenter(this)
    }

    fun getData(){
        presenter.getCity(this,"query","")
        presenter.getDistrict(this,"query","","")
        presenter.getCommune(this,"query","","")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var  aa = dataUserInfo
        var bundle :Bundle ?=intent.extras
        var isFirstSignUp = bundle!!.getBoolean(IS_FIRST_SIGN_UP)
        if(isFirstSignUp){
            Dialogs.showDialogSuccessSignUp(this,{ dialogNew: Dialog ->
                dialogNew.dismiss()
            })
        }
        getData()
        button_home.setOnClickListener(View.OnClickListener {
           CommonUtil.setColorImageSvg(this,img_button_home, R.color.text_forgot_pass, txt_button_home)
            CommonUtil.setColorImageSvg(this,img_button_offer, R.color.button_main_hide, txt_button_offer)
            CommonUtil.setColorImageSvg(this,img_button_sale, R.color.button_main_hide, txt_button_sale)
            CommonUtil.setColorImageSvg(this,img_button_account, R.color.button_main_hide, txt_button_account)
            addFragment(0)
        })
        button_offer.setOnClickListener(View.OnClickListener {
            CommonUtil.setColorImageSvg(this,img_button_home, R.color.button_main_hide, txt_button_home)
            CommonUtil.setColorImageSvg(this,img_button_offer, R.color.text_forgot_pass, txt_button_offer)
            CommonUtil.setColorImageSvg(this,img_button_sale, R.color.button_main_hide, txt_button_sale)
            CommonUtil.setColorImageSvg(this,img_button_account, R.color.button_main_hide, txt_button_account)
            addFragment(1)
        })
        button_sale.setOnClickListener(View.OnClickListener {
            CommonUtil.setColorImageSvg(this,img_button_home, R.color.button_main_hide, txt_button_home)
            CommonUtil.setColorImageSvg(this,img_button_offer, R.color.button_main_hide, txt_button_offer)
            CommonUtil.setColorImageSvg(this,img_button_sale, R.color.text_forgot_pass, txt_button_sale)
            CommonUtil.setColorImageSvg(this,img_button_account, R.color.button_main_hide, txt_button_account)
            addFragment(2)
        })
        button_account.setOnClickListener(View.OnClickListener {
            CommonUtil.setColorImageSvg(this,img_button_home, R.color.button_main_hide, txt_button_home)
            CommonUtil.setColorImageSvg(this,img_button_offer, R.color.button_main_hide, txt_button_offer)
            CommonUtil.setColorImageSvg(this,img_button_sale, R.color.button_main_hide, txt_button_sale)
            CommonUtil.setColorImageSvg(this,img_button_account, R.color.text_forgot_pass, txt_button_account)
            addFragment(3)
        })


        button_create_new.setOnClickListener(View.OnClickListener {
            val intent = Intent(this!!.applicationContext, CreateOfferActivity::class.java)
            startActivity(intent)
        })

        button_home.callOnClick()
    }


    interface OnBackPressedListner {
        fun onBackPressedFragment(): Boolean
    }
    override fun addFragment(pos:Int) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if(pos ==0){
            ft.replace(R.id.container_frame, HomeFragment())
        }else if(pos ==1){
            ft.replace(R.id.container_frame, OfferFragment())
        }else if(pos ==2){
            ft.replace(R.id.container_frame, SaleFragment())
        }else if(pos ==3){
            ft.replace(R.id.container_frame, ProfileFragment())
        }else if(pos ==4){
            ft.replace(R.id.container_frame, OrderFragment())
        }
        ft.commitAllowingStateLoss()
    }

}
