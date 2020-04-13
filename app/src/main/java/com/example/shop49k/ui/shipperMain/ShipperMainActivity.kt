package com.example.shop49k.ui.shipperMain

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.ui.fragment.delivery.DeliveryFragment
import com.example.shop49k.ui.fragment.profileShipper.ProfileShipperFragment
import com.example.shop49k.ui.main.MainPresenter
import com.example.shop49k.ui.main.MainView
import com.example.shop49k.ui.smallActivity.createOffer.CreateOfferActivity
import com.example.shop49k.ui.smallActivity.qrCode.QrCodeActivity
import com.example.shop49k.utils.IS_FIRST_SIGN_UP
import com.example.shop49k.utils.`object`.CommonUtil
import com.example.shop49k.utils.`object`.Dialogs
import kotlinx.android.synthetic.main.actitvity_main_shipper.*
import java.io.Serializable

class ShipperMainActivity : BaseActivity<ShipperMainPresenter>(), ShipperMainView, Serializable {

    override fun instantiatePresenter(): ShipperMainPresenter {
        return ShipperMainPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actitvity_main_shipper)

        var bundle :Bundle ?=intent.extras
        var isFirstSignUp = bundle!!.getBoolean(IS_FIRST_SIGN_UP)
        if(isFirstSignUp){
            Dialogs.showDialogSuccessSignUp(this,{ dialogNew: Dialog ->
                dialogNew.dismiss()
            })
        }

        button_delivery.setOnClickListener(View.OnClickListener {
            CommonUtil.setColorImageSvg(this,img_button_delivery, R.color.text_forgot_pass, txt_button_delivery)
            CommonUtil.setColorImageSvg(this,img_button_account, R.color.button_main_hide, txt_button_account)
            addFragment(0)
        })
        button_account.setOnClickListener(View.OnClickListener {
            CommonUtil.setColorImageSvg(this,img_button_delivery, R.color.button_main_hide, txt_button_delivery)
            CommonUtil.setColorImageSvg(this,img_button_account, R.color.text_forgot_pass, txt_button_account)
            addFragment(1)
        })
        button_scan.setOnClickListener(View.OnClickListener {
            val intent = Intent(this!!.applicationContext, QrCodeActivity::class.java)
            startActivity(intent)
        })
        button_delivery.callOnClick()
    }

    interface OnBackPressedListner {
        fun onBackPressedFragment(): Boolean
    }
    override fun addFragment(pos:Int) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if(pos ==0){
            ft.replace(R.id.container_frame, DeliveryFragment())
        }else if(pos ==1){
            ft.replace(R.id.container_frame, ProfileShipperFragment())
        }
        ft.commitAllowingStateLoss()
    }
}