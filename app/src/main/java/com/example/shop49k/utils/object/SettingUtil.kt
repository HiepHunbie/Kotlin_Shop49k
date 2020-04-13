package com.example.shop49k.utils.`object`

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.KeyEvent
import android.view.View
import android.widget.CompoundButton
import com.example.shop49k.R
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.ui.shipperMain.ShipperMainActivity
import kotlinx.android.synthetic.main.dialog_change_password.view.*
import kotlinx.android.synthetic.main.dialog_contact.view.*
import kotlinx.android.synthetic.main.dialog_new_password.view.*
import kotlinx.android.synthetic.main.dialog_new_password.view.edt_new_pass_word
import kotlinx.android.synthetic.main.dialog_setting.view.*
import kotlinx.android.synthetic.main.dialog_success_change_password.view.*

object SettingUtil {

    fun showDialogSetting(parentActivity: Activity, clickBackListener: (Dialog) -> Unit
                           , clickChangePassword: (Dialog) -> Unit, clickSecurity: (Dialog, Boolean) -> Unit
                           , clickNotification: (Dialog, Boolean) -> Unit, clickLogout: (Dialog) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_setting, null)
        val mDialog = Dialog(parentActivity, R.style.DialogCustom)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        mDialogView.img_back_setting.setOnClickListener(View.OnClickListener {
            clickBackListener(mDialog)
        })
        mDialogView.layout_setting_password.setOnClickListener(View.OnClickListener {
            clickChangePassword(mDialog)
        })

        mDialogView.swt_security.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                clickSecurity(mDialog,true)
            }else{
                clickSecurity(mDialog,false)
            }
        })
        mDialogView.swt_notification.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                clickNotification(mDialog,true)
            }else{
                clickNotification(mDialog,false)
            }
        })

        mDialogView.txt_setting_logout.setOnClickListener(View.OnClickListener {
            clickLogout(mDialog)
        })
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogAddOldPassword(parentActivity: Activity, clickBackListener: (Dialog) -> Unit
                          , clickContinue: (Dialog, String) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_change_password, null)
        val mDialog = Dialog(parentActivity)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        var isShowedPass:Boolean = false
        mDialogView.txt_back_change_password.setOnClickListener(View.OnClickListener {
            clickBackListener(mDialog)
        })
        mDialogView.txt_continue_change_password.setOnClickListener(View.OnClickListener {
            clickContinue(mDialog, mDialogView.edt_old_pass_word.text.toString().trim())
        })
        mDialogView.img_show_old_pass.setOnClickListener(View.OnClickListener {
            if(isShowedPass){
                isShowedPass = false
                mDialogView.edt_old_pass_word.setTransformationMethod( PasswordTransformationMethod())
            }else{
                isShowedPass = true
                mDialogView.edt_old_pass_word.setTransformationMethod( HideReturnsTransformationMethod())
            }
        })
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogAddNewPassword(parentActivity: Activity, clickDone: (Dialog, String, String) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_new_password, null)
        val mDialog = Dialog(parentActivity)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        var isShowedPass:Boolean = false
        var isShowedPassConfirm:Boolean = false
        mDialogView.txt_done_new_password.setOnClickListener(View.OnClickListener {
            if(mDialogView.edt_new_pass_word.text.toString().trim().equals(mDialogView.edt_confirm_pass_word.text.toString().trim())){
                mDialogView.txt_error_confirm_password.visibility = View.GONE
                mDialogView.edt_confirm_pass_word.setBackgroundResource(R.drawable.custom_edittext_login)
                clickDone(mDialog, mDialogView.edt_new_pass_word.text.toString().trim(),mDialogView.edt_confirm_pass_word.text.toString().trim())
            }else{
                mDialogView.txt_error_confirm_password.visibility = View.VISIBLE
                mDialogView.edt_confirm_pass_word.setBackgroundResource(R.drawable.custom_edittext_login_red)
            }
        })
        mDialogView.img_show_new_pass.setOnClickListener(View.OnClickListener {
            if(isShowedPass){
                isShowedPass = false
                mDialogView.edt_new_pass_word.setTransformationMethod( PasswordTransformationMethod())
            }else{
                isShowedPass = true
                mDialogView.edt_new_pass_word.setTransformationMethod( HideReturnsTransformationMethod())
            }
        })

        mDialogView.img_show_confirm_pass.setOnClickListener(View.OnClickListener {
            if(isShowedPassConfirm){
                isShowedPassConfirm = false
                mDialogView.edt_confirm_pass_word.setTransformationMethod( PasswordTransformationMethod())
            }else{
                isShowedPassConfirm = true
                mDialogView.edt_confirm_pass_word.setTransformationMethod( HideReturnsTransformationMethod())
            }
        })
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogContact(parentActivity: MainActivity, clickBackListener: (Dialog) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_contact, null)
        val mDialog = Dialog(parentActivity, R.style.DialogCustom)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        mDialogView.txt_contact_support.setText(parentActivity!!.dataUserInfo!!.data.phone)
        mDialogView.txt_contact_email.setText(parentActivity!!.dataUserInfo!!.data.email)
        mDialogView.txt_contact_facebook.setText("")
        mDialogView.img_back_contact.setOnClickListener(View.OnClickListener {
            clickBackListener(mDialog)
        })
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogContactShipper(parentActivity: ShipperMainActivity, clickBackListener: (Dialog) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_contact, null)
        val mDialog = Dialog(parentActivity, R.style.DialogCustom)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        mDialogView.txt_contact_support.setText(parentActivity!!.dataUserInfo!!.data.phone)
        mDialogView.txt_contact_email.setText(parentActivity!!.dataUserInfo!!.data.email)
        mDialogView.txt_contact_facebook.setText("")
        mDialogView.img_back_contact.setOnClickListener(View.OnClickListener {
            clickBackListener(mDialog)
        })
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogSuccessChangePassword(parentActivity: Activity, clickDoneListener: (Dialog) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_success_change_password, null)
        val mDialog = Dialog(parentActivity)
        mDialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
        mDialogView.txt_done_success_otp.setOnClickListener {
            //            mDialog.dismiss()
            clickDoneListener(mDialog)
        }
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }
}