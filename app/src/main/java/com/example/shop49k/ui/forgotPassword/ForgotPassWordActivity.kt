package com.example.shop49k.ui.forgotPassword

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.model.forgotPassword.ForgotCompleteInput
import com.example.shop49k.model.forgotPassword.ForgotInput
import com.example.shop49k.model.forgotPassword.ForgotResult
import com.example.shop49k.ui.login.LoginActivity
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.utils.`object`.Dialogs
import com.example.shop49k.utils.IS_FIRST_SIGN_UP
import com.example.shop49k.utils.`object`.CommonUtil
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.layout_confirm_otp.*
import kotlinx.android.synthetic.main.layout_forgot_password.*
import kotlinx.android.synthetic.main.layout_forgot_password.txt_continue_forgot_pass
import kotlinx.android.synthetic.main.layout_get_otp.*
import kotlinx.android.synthetic.main.layout_get_otp.edt_user_name

class ForgotPassWordActivity : BaseActivity<ForgotPassWordPresenter>(), ForgotPassWordView {
    override fun forgotPasswordSuccess(forgotResult: ForgotResult) {
        prg_forgot_password.visibility = View.GONE
        layout_forgot_password.visibility = View.GONE
        layout_get_otp.visibility = View.VISIBLE
        layout_confirm_otp.visibility = View.GONE
        if(isValidateEmail){
            layout_get_otp_by_email.visibility = View.VISIBLE
            layout_get_otp_by_phone.visibility = View.GONE
            edt_user_name.setText(edt_user_name_forgot_pass.text.toString().trim())
        }else{
            layout_get_otp_by_email.visibility = View.GONE
            layout_get_otp_by_phone.visibility = View.VISIBLE
            edt_your_phone.setText(edt_user_name_forgot_pass.text.toString().trim())
        }
    }

    override fun forgotPasswordError(forgotResult: String) {
        prg_forgot_password.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(this!!,forgotResult)
    }

    override fun forgotPasswordCompleteSuccess(forgotResult: ForgotResult) {
        prg_forgot_password.visibility = View.GONE
        Dialogs.showDialogSuccessOtp(this!!,{ dialogNew: Dialog ->
            dialogNew.dismiss()
            val intent = Intent(applicationContext, LoginActivity::class.java)
//            intent.putExtra(IS_FIRST_SIGN_UP,false)
            startActivity(intent)
            finish()
        })
    }

    override fun instantiatePresenter(): ForgotPassWordPresenter {
        return ForgotPassWordPresenter(this)
    }

    var isValidateEmail = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        initView()

    }

    fun initView(){
        img_back_forgot_pass.setOnClickListener(View.OnClickListener {
            finish()
        })

        txt_back_to_login.setOnClickListener(View.OnClickListener {
            finish()
        })

        txt_continue_forgot_pass.setOnClickListener(View.OnClickListener {
//            if(isValidateEmail){
                var forgotInput = ForgotInput(edt_user_name_forgot_pass.text.toString().trim())
                presenter!!.forgotPassword(this,forgotInput)
                prg_forgot_password.visibility = View.VISIBLE
//            }
        })


        img_back_get_otp.setOnClickListener(View.OnClickListener {
            layout_forgot_password.visibility = View.VISIBLE
            layout_get_otp.visibility = View.GONE
            layout_confirm_otp.visibility = View.GONE
        })

        txt_continue_get_otp.setOnClickListener(View.OnClickListener {
            layout_forgot_password.visibility = View.GONE
            layout_get_otp.visibility = View.GONE
            layout_confirm_otp.visibility = View.VISIBLE
        })

        img_back_confirm_otp.setOnClickListener(View.OnClickListener {
            layout_forgot_password.visibility = View.GONE
            layout_get_otp.visibility = View.VISIBLE
            layout_confirm_otp.visibility = View.GONE
        })

        txt_confirm.setOnClickListener(View.OnClickListener {
            if(otp_view.text.toString().isNotEmpty()){
                var forgorInput = ForgotCompleteInput(otp_view.text.toString().trim())
                presenter!!.forgotPasswordComplete(this,forgorInput)
                prg_forgot_password.visibility = View.VISIBLE
            }
        })

        txt_resend_otp.setOnClickListener(View.OnClickListener {
            var forgotInput = ForgotInput(edt_user_name_forgot_pass.text.toString().trim())
            presenter!!.forgotPassword(this,forgotInput)
            prg_forgot_password.visibility = View.VISIBLE
        })

        rdo_get_otp_from_mobile.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rdo_get_otp_from_email.isChecked = false
            }else{
                rdo_get_otp_from_email.isChecked = true
            }
        }
        rdo_get_otp_from_email.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rdo_get_otp_from_mobile.isChecked = false
            }else{
                rdo_get_otp_from_mobile.isChecked = true
            }
        }
        edt_user_name_forgot_pass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(edt_user_name_forgot_pass.text.toString().isNotEmpty()){
                    if(CommonUtil.isValidEmail(edt_user_name_forgot_pass.text.toString().trim())){
                        img_show_pass_forgot_pass.setImageResource(R.drawable.ic_checked)
                        img_show_pass_forgot_pass.visibility = View.VISIBLE
                        isValidateEmail = true
                    }else{
                        img_show_pass_forgot_pass.setImageResource(R.drawable.ic_uncheck)
                        img_show_pass_forgot_pass.visibility = View.GONE
                        isValidateEmail = false
                    }
                }else{
                    img_show_pass_forgot_pass.setImageResource(R.drawable.ic_uncheck)
                    img_show_pass_forgot_pass.visibility = View.GONE
                    isValidateEmail = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}