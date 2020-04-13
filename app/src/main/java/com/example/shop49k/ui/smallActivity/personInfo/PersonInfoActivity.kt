package com.example.shop49k.ui.smallActivity.personInfo

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.model.bottomPopup.BottomPopupModel
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.image.ImageDetailProfileModel
import com.example.shop49k.model.image.uploadImage.ImageUploadResult
import com.example.shop49k.model.offer.AddNewData
import com.example.shop49k.model.personInfo.PersonInfoInput
import com.example.shop49k.model.personInfo.PersonInforResult
import com.example.shop49k.utils.TOKEN
import com.example.shop49k.utils.`object`.*
import kotlinx.android.synthetic.main.dialog_image_detail.*
import kotlinx.android.synthetic.main.dialog_person_info.*
import kotlinx.android.synthetic.main.dialog_person_info.img_add_image_before_citizen_id
import kotlinx.android.synthetic.main.dialog_person_info.img_add_image_behind_citizen_id
import kotlinx.android.synthetic.main.dialog_person_info.layout_add_image_before_citizen_id
import kotlinx.android.synthetic.main.dialog_person_info.layout_add_image_behind_citizen_id
import kotlinx.android.synthetic.main.dialog_person_info.prg_before_citizen_id
import kotlinx.android.synthetic.main.dialog_person_info.prg_behind_citizen_id
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PersonInfoActivity : BaseActivity<PersonInfoPresenter>(), PersonInfoView {
    override fun uploadSuccess(forgotResult: ImageUploadResult) {
        if(isInfrontOfCitizenId){
            fontside_image_identity_cart = forgotResult.data[0].url
            Image.loadImageGlide(this,forgotResult.data[0].url,img_add_image_before_citizen_id!!,prg_before_citizen_id!!)
        }else{
            backside_image_identity_cart = forgotResult.data[0].url
            Image.loadImageGlide(this,forgotResult.data[0].url,img_add_image_behind_citizen_id!!,prg_behind_citizen_id!!)
        }
        prg_person_info.visibility = View.GONE
    }

    override fun uploadError(forgotResult: String) {
        prg_person_info.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(this!!,forgotResult)
    }

    override fun updateUserSuccess(forgotResult: PersonInforResult) {
        Dialogs.showToastInformation(this,getString(R.string.update_person_info_success))
        isEditting = false
        txt_save_edit_person_info.setText(getString(R.string.edit))
        enableEdit(false)
        prg_person_info.visibility = View.GONE
    }

    override fun updateUserError(forgotResult: String) {
        prg_person_info.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(this!!,forgotResult)
    }

    override fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectDistrict.clear()
        for(item in dataCityDetail){
            listSelectDistrict.add(BottomPopupModel(item.id,item.text))
        }
        if(!isEditting){
            for (i in listSelectDistrict.indices){
                var item = listSelectDistrict[i]
                if(dataUserInfo!!.data.district_id.toInt() == item.id){
                    txt_select_district.setText(item.value)
                }
            }
        }
    }

    override fun getCommueSuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectWard.clear()
        for(item in dataCityDetail){
            listSelectWard.add(BottomPopupModel(item.id,item.text))
        }
        if(!isEditting){
        for (i in listSelectWard.indices){
            var item = listSelectWard[i]
            if(dataUserInfo!!.data.commune_id.toInt() == item.id){
                txt_select_ward.setText(item.value)
            }
        }
        }
    }

    override fun getCitySuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectProvice.clear()
        for(item in dataCityDetail){
            listSelectProvice.add(BottomPopupModel(item.id,item.text))
        }
        if(!isEditting) {
            for (i in listSelectProvice.indices) {
                var item = listSelectProvice[i]
                if (dataUserInfo!!.data.city_id.toInt() == item.id) {
                    txt_select_provice_and_city.setText(item.value)
                }
            }
        }
    }

    override fun instantiatePresenter(): PersonInfoPresenter {
        return PersonInfoPresenter(this)
    }

    var isEditting = false
    var isInfrontOfCitizenId = false
    var listSelectSex : ArrayList<BottomPopupModel> = ArrayList()
    var listSelectProvice : ArrayList<BottomPopupModel> = ArrayList()
    var listSelectDistrict : ArrayList<BottomPopupModel> = ArrayList()
    var listSelectWard : ArrayList<BottomPopupModel> = ArrayList()
    var listBank : ArrayList<BottomPopupModel> = ArrayList()
    var listSelectAddress : ArrayList<BottomPopupModel> = ArrayList()
    var full_name = ""
    var dob = ""
    var fontside_image_identity_cart = ""
    var backside_image_identity_cart = ""
    var gender = ""
    var city_id = ""
    var district_id = ""
    var commune_id = ""
    var address = ""
    var shop_created = ""
    var description = ""
    var shop_address = ""
    var business_area = ""
    var avatar = ""
    var merchant_registration_front_site = ""
    var merchant_registration_back_site = ""
    var bank_account_number = ""
    var bank_id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_person_info)

        layout_add_image_before_citizen_id.isEnabled = false
        layout_add_image_behind_citizen_id.isEnabled = false

        img_add_image_before_citizen_id!!.scaleType = ImageView.ScaleType.CENTER_CROP
        img_add_image_behind_citizen_id!!.scaleType = ImageView.ScaleType.CENTER_CROP

        edt_full_name.setText(dataUserInfo!!.data.fullname ?: "")
        edt_birthday.setText(DateUtil.convertDateToStringBirthDay(dataUserInfo!!.data.dob ?: ""))
        if(dataUserInfo!!.data.gender == 0){
            txt_select_sex.setText(getString(R.string.male))
        }else if(dataUserInfo!!.data.gender == 1){
            txt_select_sex.setText(getString(R.string.remale))
        }else{
            txt_select_sex.setText(getString(R.string.other))
        }
        edt_edit_citizen_id.setText(dataUserInfo!!.data.identity_number ?: "")
        edt_phone.setText(dataUserInfo!!.data.phone ?: "")
        edt_email.setText(dataUserInfo!!.data.email ?: "")

        if(dataUserInfo!!.data.fontside_image_identity_cart != null){
            Image.loadImageGlide(this,dataUserInfo!!.data.fontside_image_identity_cart,img_add_image_before_citizen_id!!,prg_before_citizen_id!!)
        }
        if(dataUserInfo!!.data.backside_image_identity_cart != null){
            Image.loadImageGlide(this,dataUserInfo!!.data.backside_image_identity_cart,img_add_image_behind_citizen_id!!,prg_behind_citizen_id!!)
        }
        txt_select_address.setText(dataUserInfo!!.data.address)

        presenter!!.getCity(this!!,"query","")
        presenter.getDistrict(this,"query","",dataUserInfo!!.data.city_id ?: "")
        presenter.getCommune(this,"query","",dataUserInfo!!.data.district_id ?: "")

        listSelectSex.add(BottomPopupModel(0,getString(R.string.male)))
        listSelectSex.add(BottomPopupModel(1,getString(R.string.remale)))
        listSelectSex.add(BottomPopupModel(2,getString(R.string.other)))

        for(item in masterData!!.data.bank_list){
            listBank.add(BottomPopupModel(item.id,item.name))
        }

        txt_save_edit_person_info.setOnClickListener(View.OnClickListener {
            if(isEditting){
                full_name = edt_full_name.text.toString().trim()
                address = txt_select_address.text.toString().trim()
                avatar = dataUserInfo!!.data.avatar ?: ""
                bank_account_number = txt_bank_account.text.toString().trim()
                shop_created = dataUserInfo!!.data.shop_created ?: ""
                description = dataUserInfo!!.data.description ?: ""
                shop_address = dataUserInfo!!.data.shop_address ?: ""
                business_area = dataUserInfo!!.data.business_area ?: ""
                merchant_registration_front_site = dataUserInfo!!.data.merchant_registration_front_site ?: ""
                merchant_registration_back_site = dataUserInfo!!.data.merchant_registration_back_site ?: ""
                var personInfoInput = PersonInfoInput(full_name,dob,fontside_image_identity_cart,backside_image_identity_cart,gender,city_id,
                    district_id,commune_id,address,shop_created,description,shop_address,business_area,avatar,merchant_registration_front_site,
                    merchant_registration_back_site,bank_account_number,bank_id.toString(),dataUserInfo!!.data.shop_city_id,
                    dataUserInfo!!.data.shop_district_id,dataUserInfo!!.data.shop_commune_id)
                presenter!!.updatePersonInfo(this, prefs!!.getString(TOKEN,""),personInfoInput)
                prg_person_info.visibility = View.VISIBLE
            }else{
                isEditting = true
                txt_save_edit_person_info.setText(getString(R.string.save))
                enableEdit(true)
            }
        })

        txt_select_sex.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listSelectSex, getString(R.string.select_sex),{ dialogNew: Dialog, idSelect : Int ->
                txt_select_sex.setText(listSelectSex[idSelect].value)
                gender = listSelectSex[idSelect].id.toString()
                dialogNew.dismiss()
            })
        })
        txt_select_provice_and_city.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listSelectProvice, getString(R.string.select_provice_and_city),{ dialogNew: Dialog, idSelect : Int ->
                txt_select_provice_and_city.setText(listSelectProvice[idSelect].value)
                presenter.getDistrict(this,"query","",listSelectProvice[idSelect].id.toString())
                city_id = listSelectProvice[idSelect].id.toString()
                dialogNew.dismiss()
            })
        })
        txt_select_district.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listSelectDistrict, getString(R.string.select_district),{ dialogNew: Dialog, idSelect : Int ->
                txt_select_district.setText(listSelectDistrict[idSelect].value)
                district_id = listSelectDistrict[idSelect].id.toString()
                presenter.getCommune(this,"query","",listSelectDistrict[idSelect].id.toString())
                dialogNew.dismiss()
            })
        })
        txt_select_ward.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listSelectWard, getString(R.string.select_ward),{ dialogNew: Dialog, idSelect : Int ->
                txt_select_ward.setText(listSelectWard[idSelect].value)
                commune_id = listSelectWard[idSelect].id.toString()
                dialogNew.dismiss()
            })
        })
        txt_bank.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listBank, getString(R.string.select_bank),{ dialogNew: Dialog, idSelect : Int ->
                txt_bank.setText(listBank[idSelect].value)
                bank_id = listBank[idSelect].id.toString()
                dialogNew.dismiss()
            })
        })

        layout_add_image_before_citizen_id.setOnClickListener(View.OnClickListener {
            isInfrontOfCitizenId = true
            Dialogs.showDialogBottomSelectPicture(this!!,{ dialogNew: Dialog, pos : Int ->
                if(pos == 0){
                    GetImageUtil.captureImage("citized_1",this)
                }else if(pos == 1){
                    GetImageUtil.openAlbum(this)
                }
                dialogNew.dismiss()
            })
        })

        layout_add_image_behind_citizen_id.setOnClickListener(View.OnClickListener {
            isInfrontOfCitizenId = false
            Dialogs.showDialogBottomSelectPicture(this!!,{ dialogNew: Dialog, pos : Int ->
                if(pos == 0){
                    GetImageUtil.captureImage("citized_2",this)
                }else if(pos == 1){
                    GetImageUtil.openAlbum(this)
                }
                dialogNew.dismiss()
            })
        })

        img_calendar_birthday.setOnClickListener(View.OnClickListener {
            Offer.showDialogSelectCalendar(this,{ time:String, dialogNew: Dialog ->
                edt_birthday.setText(time)
                dob = DateUtil.convertDateToStringBirthDayInput(time)
                dialogNew.dismiss()
            })
        })
        img_back_person_info.setOnClickListener(View.OnClickListener {
            finish()
        })

        CommonUtil.hideKeyboard(edt_full_name,this@PersonInfoActivity)
    }

    fun enableEdit(enable : Boolean){
        if(enable){
            edt_full_name.isEnabled = true
            edt_birthday.isEnabled = true
            txt_select_sex.isEnabled = true
            edt_edit_citizen_id.isEnabled = true
            layout_add_image_before_citizen_id.isEnabled = true
            layout_add_image_before_citizen_id.visibility = View.VISIBLE
            layout_add_image_behind_citizen_id.isEnabled = true
            layout_add_image_behind_citizen_id.visibility = View.VISIBLE
            img_add_image_before_citizen_id.visibility = View.GONE
            img_add_image_behind_citizen_id.visibility = View.GONE
            edt_phone.isEnabled = true
            edt_email.isEnabled = true
            txt_select_provice_and_city.isEnabled = true
            txt_select_district.isEnabled = true
            txt_select_ward.isEnabled = true
            txt_select_address.isEnabled = true
            img_calendar_birthday.visibility = View.VISIBLE
            txt_bank_account.isEnabled = true
            txt_bank.isEnabled = true
        }else{
            edt_full_name.isEnabled = false
            edt_birthday.isEnabled = false
            txt_select_sex.isEnabled = false
            edt_edit_citizen_id.isEnabled = false
            layout_add_image_before_citizen_id.isEnabled = false
            layout_add_image_before_citizen_id.visibility = View.GONE
            layout_add_image_behind_citizen_id.isEnabled = false
            layout_add_image_behind_citizen_id.visibility = View.GONE
            img_add_image_before_citizen_id.visibility = View.VISIBLE
            img_add_image_behind_citizen_id.visibility = View.VISIBLE
            edt_phone.isEnabled = false
            edt_email.isEnabled = false
            txt_select_provice_and_city.isEnabled = false
            txt_select_district.isEnabled = false
            txt_select_ward.isEnabled = false
            txt_select_address.isEnabled = false
            img_calendar_birthday.visibility = View.GONE
            txt_bank_account.isEnabled = false
            txt_bank.isEnabled = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GetImageUtil.FINAL_TAKE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(GetImageUtil.imageUri!!))
//                    picture!!.setImageBitmap(bitmap)
                    var file = File(GetImageUtil.imageUri!!.path)
                    var fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
                    var filePart = MultipartBody.Part.createFormData("file[]", file.getName(), fileReqBody)
                    var description = RequestBody.create(MediaType.parse("text/plain"), "image-type")
                    prg_person_info.visibility = View.VISIBLE
                    presenter!!.uploadImage(this,prefs!!.getString(TOKEN, ""),filePart,fileReqBody)
                    if(isInfrontOfCitizenId){
                        img_add_image_before_citizen_id.setImageBitmap(bitmap)
                        layout_add_image_before_citizen_id.visibility = View.GONE
                        img_add_image_before_citizen_id.visibility = View.VISIBLE
                    }else{
                        img_add_image_behind_citizen_id.setImageBitmap(bitmap)
                        layout_add_image_behind_citizen_id.visibility = View.GONE
                        img_add_image_behind_citizen_id.visibility = View.VISIBLE
                    }
                }
            GetImageUtil.FINAL_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
//                        4.4以上
                        var filePath = GetImageUtil.handleImageOnKitkat(data,this)!!
                        var file = File(filePath)
                        var fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
                        var filePart = MultipartBody.Part.createFormData("file[]", file.getName(), fileReqBody)
                        var description = RequestBody.create(MediaType.parse("text/plain"), "image-type")
                        prg_person_info.visibility = View.VISIBLE
                        presenter!!.uploadImage(this,prefs!!.getString(TOKEN, ""),filePart,fileReqBody)
                        if(isInfrontOfCitizenId){
                            Glide.with(this).load(GetImageUtil.handleImageOnKitkat(data,this)).into(img_add_image_before_citizen_id)
                            layout_add_image_before_citizen_id.visibility = View.GONE
                            img_add_image_before_citizen_id.visibility = View.VISIBLE
                        }else{
                            Glide.with(this).load(GetImageUtil.handleImageOnKitkat(data,this)).into(img_add_image_behind_citizen_id)
                            layout_add_image_behind_citizen_id.visibility = View.GONE
                            img_add_image_behind_citizen_id.visibility = View.VISIBLE
                        }
                    }
                    else{
//                        4.4以下
                        GetImageUtil.handleImageBeforeKitkat(data)
                    }
                }
        }
    }
}