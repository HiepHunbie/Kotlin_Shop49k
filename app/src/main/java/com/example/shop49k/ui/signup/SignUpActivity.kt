package com.example.shop49k.ui.signup

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.ArrayAdapter
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.model.bottomPopup.BottomPopupModel
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.offer.AddNewData
import com.example.shop49k.model.register.RegisterInput
import com.example.shop49k.model.register.RegisterResult
import com.example.shop49k.ui.login.LoginActivity
import com.example.shop49k.ui.login.LoginPresenter
import com.example.shop49k.ui.login.LoginView
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.utils.BASE_URL
import com.example.shop49k.utils.IS_FIRST_SIGN_UP
import com.example.shop49k.utils.TYPE_USER
import com.example.shop49k.utils.`object`.CommonUtil
import com.example.shop49k.utils.`object`.Dialogs
import com.example.shop49k.utils.`object`.PersonInfoUtil
import kotlinx.android.synthetic.main.activity_sign_up_account.*
import kotlinx.android.synthetic.main.layout_confirm_otp.*
import kotlinx.android.synthetic.main.layout_new_password.*
import kotlinx.android.synthetic.main.layout_new_password.edt_new_pass_word
import kotlinx.android.synthetic.main.layout_sign_up_account.*
import kotlinx.android.synthetic.main.layout_sign_up_company.*
import kotlinx.android.synthetic.main.layout_sign_up_person.*

class SignUpActivity : BaseActivity<SignUpPresenter>(), SignUpView {
    override fun validateUserAcountSuccess(registerResult: RegisterResult) {
        layout_sign_up_account.visibility = View.GONE
        layout_confirm_otp.visibility = View.GONE
        layout_sign_up_person.visibility = View.VISIBLE
//            layout_sign_up_person.visibility = View.GONE
        layout_sign_up_company.visibility = View.GONE
    }

    override fun validateUserUserSuccess(registerResult: RegisterResult) {
        layout_sign_up_account.visibility = View.GONE
        layout_confirm_otp.visibility = View.GONE
        layout_sign_up_person.visibility = View.GONE
        layout_sign_up_company.visibility = View.VISIBLE
        edt_your_email.setBackgroundResource(R.drawable.custom_edittext_login)
    }

    override fun validateUserError(registerResult: String) {
        Dialogs.showToastErrorNewNotListenerClick(this,registerResult)
    }

    override fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectDistrict.clear()
        for(item in dataCityDetail){
            listSelectDistrict.add(BottomPopupModel(item.id,item.text))
        }
    }

    override fun getCommueSuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectWard.clear()
        for(item in dataCityDetail){
            listSelectWard.add(BottomPopupModel(item.id,item.text))
        }
    }

    override fun getCitySuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectProvice.clear()
        for(item in dataCityDetail){
            listSelectProvice.add(BottomPopupModel(item.id,item.text))
        }
    }

    override fun registerSuccess(registerResult: RegisterResult) {
        prg_sign_up_account.visibility = View.GONE
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.putExtra(IS_FIRST_SIGN_UP,true)
        startActivity(intent)
        finish()
    }

    override fun registerError(registerResult: String) {
        prg_sign_up_account.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(this,registerResult)
    }

    override fun instantiatePresenter(): SignUpPresenter {
        return SignUpPresenter(this)
    }
    var isShowedPass : Boolean = false
    var listSelectProvice : ArrayList<BottomPopupModel> = ArrayList()
    var listSelectDistrict : ArrayList<BottomPopupModel> = ArrayList()
    var listSelectWard : ArrayList<BottomPopupModel> = ArrayList()
    var listBussinessAreas : ArrayList<BottomPopupModel> = ArrayList()
    var registerInput : RegisterInput? = null
    var city_id = 0
    var district_id = 0
    var commune_id = 0
    var group_id = 0
    var address = ""
    var business_area = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_account)
        initView()

    }

    fun initView(){
        var typeId = prefs!!.getInt(TYPE_USER,0)
        if(typeId == 1){
            group_id = 3
        }else if(typeId == 2){
            group_id = 5
        }
        registerInput = RegisterInput("","","","","","","","","","","","","","")
        presenter!!.getCity(this!!,"query","")
        if(masterData != null){
            for(item in masterData!!.data.categories){
                listBussinessAreas.add(BottomPopupModel(item.id,item.title))
            }
        }

        txt_back_to_login.setOnClickListener(View.OnClickListener {
            finish()
        })
        img_back_sign_up_account.setOnClickListener(View.OnClickListener {
            finish()
        })

        img_back_sign_up_shop.setOnClickListener(View.OnClickListener {
            layout_sign_up_account.visibility = View.GONE
            layout_confirm_otp.visibility = View.GONE
            layout_sign_up_person.visibility = View.VISIBLE
            layout_sign_up_company.visibility = View.GONE
        })

        img_back_sign_up_shop_info.setOnClickListener(View.OnClickListener {
            layout_sign_up_account.visibility = View.VISIBLE
            layout_confirm_otp.visibility = View.GONE
            layout_sign_up_person.visibility = View.GONE
            layout_sign_up_company.visibility = View.GONE
        })
        txt_continue_sign_up_account.setOnClickListener(View.OnClickListener {
            if(CommonUtil.isValidEmail(edt_user_name_sign_up_account.text.toString())){
                registerInput!!.email = edt_user_name_sign_up_account.text.toString().trim()
            }else{
                registerInput!!.phone = edt_user_name_sign_up_account.text.toString().trim()
            }
            presenter!!.validateUser(this,registerInput!!,1)
        })

        img_back_confirm_otp.setOnClickListener(View.OnClickListener {
            layout_sign_up_account.visibility = View.VISIBLE
            layout_confirm_otp.visibility = View.GONE
            layout_sign_up_person.visibility = View.GONE
            layout_sign_up_company.visibility = View.GONE
        })

        txt_confirm.setOnClickListener(View.OnClickListener {
            layout_sign_up_account.visibility = View.GONE
            layout_confirm_otp.visibility = View.GONE
            layout_sign_up_person.visibility = View.VISIBLE
            layout_sign_up_company.visibility = View.GONE
        })

        txt_continue.setOnClickListener(View.OnClickListener {
            if(rdo_check_sign_up_person.isChecked){
                if(CommonUtil.isValidEmail(edt_your_email.text.toString().trim())){
                    if(edt_your_name.text.toString().isEmpty()){
                        registerInput!!.full_name = " "
                    }else{
                        registerInput!!.full_name = edt_your_name.text.toString().trim()
                    }
                    if(edt_citizen_id.text.toString().isEmpty()){
                        registerInput!!.identity_number = " "
                    }else{
                        registerInput!!.identity_number = edt_citizen_id.text.toString().trim()
                    }
                    if(edt_your_email.text.toString().isEmpty()){
                        registerInput!!.email = " "
                    }else{
                        registerInput!!.email = edt_your_email.text.toString().trim()
                    }
                    if(edt_new_pass_word.text.toString().isEmpty()){
                        registerInput!!.password = " "
                    }else{
                        registerInput!!.password = edt_new_pass_word.text.toString().trim()
                    }
                    edt_your_email.setBackgroundResource(R.drawable.custom_edittext_login)
                    presenter!!.validateUser(this,registerInput!!,2)
                }else{
                    edt_your_email.setBackgroundResource(R.drawable.custom_edittext_login_red)
                }

            }
        })
        txt_done.setOnClickListener(View.OnClickListener {
            registerInput!!.shop_name = edt_your_name_shop.text.toString().trim()
            registerInput!!.merchant_id = edt_citizen_id_shop.text.toString().trim()
            registerInput!!.shop_city_id = city_id.toString()
            registerInput!!.shop_district_id = district_id.toString()
            registerInput!!.shop_commune_id = commune_id.toString()
            registerInput!!.shop_address = edt_your_number_home_street.text.toString().trim()
            registerInput!!.group_id = group_id.toString()
            registerInput!!.business_area = business_area.toString()

            presenter!!.register(this,registerInput!!)
            prg_sign_up_account.visibility = View.VISIBLE
        })
//        img_show_new_pass.setOnClickListener(View.OnClickListener {
//            if(isShowedPass){
//                isShowedPass = false
//                edt_new_pass_word.setTransformationMethod( PasswordTransformationMethod())
//            }else{
//                isShowedPass = true
//                edt_new_pass_word.setTransformationMethod( HideReturnsTransformationMethod())
//            }
//        })


        layout_spn_provice_and_city.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listSelectProvice, getString(R.string.select_provice_and_city),{ dialogNew: Dialog, idSelect : Int ->
                spn_provice_and_city.setText(listSelectProvice[idSelect].value)
                city_id = listSelectProvice[idSelect].id
                presenter.getDistrict(this,"query","",listSelectProvice!![idSelect].id.toString())
                dialogNew.dismiss()
            })
            CommonUtil.hideKeyboard(layout_spn_provice_and_city,this)
        })
        layout_spn_district.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listSelectDistrict, getString(R.string.select_district),{ dialogNew: Dialog, idSelect : Int ->
                spn_district.setText(listSelectDistrict[idSelect].value)
                district_id = listSelectDistrict[idSelect].id
                presenter!!.getCommune(this!!,"query","",listSelectDistrict[idSelect]!!.id.toString())
                dialogNew.dismiss()
            })
            CommonUtil.hideKeyboard(layout_spn_district,this)
        })
        layout_spn_ward.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listSelectWard, getString(R.string.select_ward),{ dialogNew: Dialog, idSelect : Int ->
                spn_ward.setText(listSelectWard[idSelect].value)
                commune_id = listSelectWard[idSelect].id
                dialogNew.dismiss()
            })
            CommonUtil.hideKeyboard(layout_spn_ward,this)
        })
        layout_spn_bussiness_areas.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listBussinessAreas, getString(R.string.spn_bussiness_areas),{ dialogNew: Dialog, idSelect : Int ->
                spn_bussiness_areas.setText(listBussinessAreas[idSelect].value)
//                group_id = listBussinessAreas[idSelect].id
                business_area = listBussinessAreas[idSelect].value
                dialogNew.dismiss()
            })
            CommonUtil.hideKeyboard(layout_spn_bussiness_areas,this)
        })
    }
}