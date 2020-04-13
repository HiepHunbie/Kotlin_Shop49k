package com.example.shop49k.ui.login

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.model.error.ErrorType
import com.example.shop49k.model.login.LoginData
import com.example.shop49k.model.login.LoginInput
import com.example.shop49k.model.masterData.MasterData
import com.example.shop49k.model.masterData.MasterDataData
import com.example.shop49k.model.userInfo.UserInfo
import com.example.shop49k.model.userInfo.UserInfoData
import com.example.shop49k.ui.forgotPassword.ForgotPassWordActivity
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.ui.shipperMain.ShipperMainActivity
import com.example.shop49k.ui.signup.SignUpActivity
import com.example.shop49k.utils.*
import com.example.shop49k.utils.`object`.Dialogs
import com.example.shop49k.utils.`object`.CommonUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginPresenter>(), LoginView {

    override fun getUserInfoSuccess(userInfo: UserInfo) {
//        dataUserInfo = userInfo
        val editor = prefs!!.edit()
        editor.putString(DATA_USER_INFO, gson!!.toJson(userInfo))
        editor.apply()
        totalCallApiAfterLogin += 1
        if(totalCallApiAfterLogin == 1){
            checkLoginWithType()
            prg_login.visibility = View.GONE
        }
    }

    override fun errorGetUserInfo(mess: String) {
        prg_login.visibility = View.GONE
    }

    fun checkLoginWithType(){
        if(typeUser == 1){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra(IS_FIRST_SIGN_UP,false)
            startActivity(intent)
            finish()
        }else if(typeUser == 2){
            val intent = Intent(applicationContext, ShipperMainActivity::class.java)
            intent.putExtra(IS_FIRST_SIGN_UP,false)
            startActivity(intent)
            finish()
        }
    }
    override fun getMasterDataSuccess(masterDataData: MasterData) {
        totalCallApiAfterLogin += 1
        masterData = masterDataData
        val editor = prefs!!.edit()
        editor.putString(MASTER_DATA, gson!!.toJson(masterDataData))
        editor.apply()
//        if(totalCallApiAfterLogin == 2){
////            checkLoginWithType()
////            prg_login.visibility = View.GONE
////        }
        prg_login.visibility = View.GONE
    }

    override fun errorGetMasterData(mess: String) {
        prg_login.visibility = View.GONE
    }

    override fun updateLogin(login: LoginData) {
        totalCallApiAfterLogin = 0
        val editor = prefs!!.edit()
        editor.putString(TOKEN, login.token)
        editor.apply()
        presenter!!.getUserInfo(this,login.token)
    }
    override fun showErrorLogin(mess: String) {
        prg_login.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(this!!,mess)
    }

    override fun instantiatePresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    var totalCallApiAfterLogin = 0
    var isShowedPass:Boolean = false
    private val PermissionsRequestCode = 123
    var typeUser = 0
    lateinit var managePermissions: ManagePermissions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }


    fun initView(){
        val list = listOf<String>(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        typeUser = prefs!!.getInt(TYPE_USER, 0)
        managePermissions = ManagePermissions(this!!,list,PermissionsRequestCode)
        if(typeUser != 0){
//            managePermissions.checkPermissions()
//            if(typeUser == 1){
//                val intent = Intent(applicationContext, MainActivity::class.java)
//                intent.putExtra(IS_FIRST_SIGN_UP,false)
//                startActivity(intent)
//                finish()
//            }else if(typeUser == 2){
//                val intent = Intent(applicationContext, ShipperMainActivity::class.java)
//                intent.putExtra(IS_FIRST_SIGN_UP,false)
//                startActivity(intent)
//                finish()
//            }

        }else{
            Dialogs.showDialogWhoAreYou(this!!,{ dialogNew: Dialog,isMerchart : Boolean ->
                if(isMerchart){
                    val editor = prefs!!.edit()
                    editor.putInt(TYPE_USER, 1)
                    editor.apply()
                }else{
                    val editor = prefs!!.edit()
                    editor.putInt(TYPE_USER, 2)
                    editor.apply()
                }
                typeUser = prefs!!.getInt(TYPE_USER, 0)
                dialogNew.dismiss()
                managePermissions.checkPermissions()
            })
        }

        img_back_login.setOnClickListener(View.OnClickListener {
            Dialogs.showDialogWhoAreYou(this!!,{ dialogNew: Dialog,isMerchart : Boolean ->
                if(isMerchart){
                    val editor = prefs!!.edit()
                    editor.putInt(TYPE_USER, 1)
                    editor.apply()
                }else{
                    val editor = prefs!!.edit()
                    editor.putInt(TYPE_USER, 2)
                    editor.apply()
                }
                typeUser = prefs!!.getInt(TYPE_USER, 0)
                dialogNew.dismiss()
                managePermissions.checkPermissions()
            })
        })

        img_show_pass.setOnClickListener(View.OnClickListener {
            if(isShowedPass){
                isShowedPass = false
                edt_pass_word.setTransformationMethod( PasswordTransformationMethod())
            }else{
                isShowedPass = true
                edt_pass_word.setTransformationMethod( HideReturnsTransformationMethod())
            }
        })
        txt_forgot_password.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, ForgotPassWordActivity::class.java)
            startActivity(intent)
        })

        txt_sign_up.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        })
        prg_login.visibility = View.VISIBLE
        presenter!!.getMasterData(this)
        txt_login.setOnClickListener(View.OnClickListener {
            //            if(validate()){
            var input : LoginInput? = null
            if(typeUser == 1){
                input = LoginInput(edt_user_name.text.toString().trim(),edt_pass_word.text.toString().trim(),3)
            }else{
                input = LoginInput(edt_user_name.text.toString().trim(),edt_pass_word.text.toString().trim(),5)
            }
            prg_login.visibility = View.VISIBLE
            val editor = prefs!!.edit()
            editor.putString(USER_NAME, edt_user_name.text.toString().trim())
            editor.putString(PASSWORD, edt_pass_word.text.toString().trim())
            editor.apply()
            presenter.login(this,input!!)
            CommonUtil.hideKeyboard(txt_login,this@LoginActivity)
//            }
        })

        var token = prefs!!.getString(TOKEN,"")
        if(token.isNotEmpty()){
            var input : LoginInput? = null
            if(typeUser == 1){
                input = LoginInput(prefs!!.getString(USER_NAME,"").trim(),prefs!!.getString(PASSWORD,"").trim(),3)
            }else{
                input = LoginInput(prefs!!.getString(USER_NAME,"").trim(),prefs!!.getString(PASSWORD,"").trim(),5)
            }
            prg_login.visibility = View.VISIBLE
            presenter.login(this,input!!)
//            checkLoginWithType()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PermissionsRequestCode ->{
                val isPermissionsGranted =managePermissions!!
                    .processPermissionsResult(requestCode,permissions,grantResults)

                if(isPermissionsGranted){
//                    addFragment(2)
//                    Dialogs.showToastErrorNewNotListenerClick(this,"Permissions ok.")
                }else{
//                    Dialogs.showToastErrorNewNotListenerClick(this,"Permissions denied.")
                }

                return
            }
        }
    }

    fun validate() : Boolean{
        var isValidate : Boolean= true
        var isValidatePassword : Boolean= true
        if(edt_user_name.text.toString().trim().isEmpty()){
            edt_user_name.setBackgroundResource(R.drawable.custom_edittext_login_red)
            isValidate = false
        }else{
            if(CommonUtil.isValidEmail(edt_user_name.text.toString().trim())){
                edt_user_name.setBackgroundResource(R.drawable.custom_edittext_login)
                isValidate = true
            }else{
                edt_user_name.setBackgroundResource(R.drawable.custom_edittext_login_red)
                isValidate = false
            }
        }
        if(edt_pass_word.text.toString().trim().isEmpty()){
            edt_pass_word.setBackgroundResource(R.drawable.custom_edittext_login_red)
            isValidatePassword = false
        }else{
            edt_pass_word.setBackgroundResource(R.drawable.custom_edittext_login)
            isValidatePassword = true
        }
        return isValidate && isValidatePassword
    }
}