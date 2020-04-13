package com.example.shop49k.ui.fragment.profileShipper

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.shop49k.R
import com.example.shop49k.base.BaseFragment
import com.example.shop49k.ui.login.LoginActivity
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.ui.smallActivity.ImageDetailProfile.ImageDetailProfileActivity
import com.example.shop49k.ui.smallActivity.personInfo.PersonInfoActivity
import com.example.shop49k.ui.smallActivity.shopInfo.ShopInfoActivity
import com.example.shop49k.utils.PASSWORD
import com.example.shop49k.utils.TOKEN
import com.example.shop49k.utils.TYPE_USER
import com.example.shop49k.utils.USER_NAME
import com.example.shop49k.utils.`object`.SettingUtil
import kotlinx.android.synthetic.main.fragment_profile_shipper.view.*

class ProfileShipperFragment : BaseFragment<ProfileShipperPresenter>(), ProfileShipperView, MainActivity.OnBackPressedListner{


    override fun onBackPressedFragment(): Boolean {
        shipperMainActivity!!.addFragment(0)
        return false
    }

    override fun instantiatePresenter(): ProfileShipperPresenter {
        return ProfileShipperPresenter(this)    }

    override fun getContext(): Context {
        return shipperMainActivity!!.getContext()
    }

    var layout_person_profile_shipper : RelativeLayout? = null
    var layout_setting_profile_shipper : RelativeLayout? = null
    var layout_contact : RelativeLayout? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_shipper, container, false)
        layout_person_profile_shipper = view.layout_person_profile_shipper
        layout_setting_profile_shipper = view.layout_setting
        layout_contact = view.layout_contact


        layout_person_profile_shipper!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(shipperMainActivity!!.applicationContext, PersonInfoActivity::class.java)
            startActivity(intent)
        })

        layout_setting_profile_shipper!!.setOnClickListener(View.OnClickListener {
            SettingUtil.showDialogSetting(shipperMainActivity!!,{ dialogSetting: Dialog ->
                dialogSetting.dismiss()
            },{ dialogSetting: Dialog ->
                SettingUtil.showDialogAddOldPassword(shipperMainActivity!!,{ dialogAddOldPassword: Dialog ->
                    dialogAddOldPassword.dismiss()
                },{ dialogAddOldPassword: Dialog , pass:String ->
                    dialogAddOldPassword.dismiss()
                    SettingUtil.showDialogAddNewPassword(shipperMainActivity!!,{ dialogAddNewPassword: Dialog, pass:String, confirmPass : String ->
                        dialogAddNewPassword.dismiss()
                    })
                })
            },{ dialogSetting: Dialog, isChecked : Boolean ->
            },{ dialogSetting: Dialog, isChecked : Boolean ->
            },{ dialogSetting: Dialog ->
                val editor = shipperMainActivity!!.prefs!!.edit()
                editor.putInt(TYPE_USER, 0)
                editor.putString(TOKEN, "")
                editor.putString(USER_NAME, "")
                editor.putString(PASSWORD, "")
                editor.apply()
                val intent = Intent(shipperMainActivity!!, LoginActivity::class.java)
                startActivity(intent)
                shipperMainActivity!!.finish()
            })
        })

        layout_contact!!.setOnClickListener(View.OnClickListener {
            SettingUtil.showDialogContactShipper(shipperMainActivity!!,{ dialogContact: Dialog ->
                dialogContact.dismiss()
            })
        })
        return view
    }
}