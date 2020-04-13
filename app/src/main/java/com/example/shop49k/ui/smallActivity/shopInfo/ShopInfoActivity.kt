package com.example.shop49k.ui.smallActivity.shopInfo

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.model.bottomPopup.BottomPopupModel
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.image.uploadImage.ImageUploadResult
import com.example.shop49k.model.personInfo.PersonInfoInput
import com.example.shop49k.model.personInfo.PersonInforResult
import com.example.shop49k.utils.TOKEN
import com.example.shop49k.utils.`object`.*
import kotlinx.android.synthetic.main.dialog_person_info.*
import kotlinx.android.synthetic.main.dialog_shop_info.*
import kotlinx.android.synthetic.main.dialog_shop_info.img_add_image_before_citizen_id
import kotlinx.android.synthetic.main.dialog_shop_info.img_add_image_behind_citizen_id
import kotlinx.android.synthetic.main.dialog_shop_info.layout_add_image_before_citizen_id
import kotlinx.android.synthetic.main.dialog_shop_info.layout_add_image_behind_citizen_id
import kotlinx.android.synthetic.main.dialog_shop_info.prg_before_citizen_id
import kotlinx.android.synthetic.main.dialog_shop_info.prg_behind_citizen_id
import kotlinx.android.synthetic.main.dialog_shop_info.txt_select_address
import kotlinx.android.synthetic.main.dialog_shop_info.txt_select_district
import kotlinx.android.synthetic.main.dialog_shop_info.txt_select_provice_and_city
import kotlinx.android.synthetic.main.dialog_shop_info.txt_select_ward
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ShopInfoActivity : BaseActivity<ShopInfoPresenter>(), ShopInfoView {
    override fun uploadSuccess(forgotResult: ImageUploadResult) {
        if(isInfrontOfCitizenId){
            merchant_registration_front_site = forgotResult.data[0].url
            Image.loadImageGlide(this,forgotResult.data[0].url,img_add_image_before_citizen_id!!,prg_before_citizen_id!!)
        }else{
            merchant_registration_back_site = forgotResult.data[0].url
            Image.loadImageGlide(this,forgotResult.data[0].url,img_add_image_behind_citizen_id!!,prg_behind_citizen_id!!)
        }
        prg_shop_info.visibility = View.GONE
    }

    override fun uploadError(forgotResult: String) {
        prg_shop_info.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(this!!,forgotResult)
    }

    override fun updateUserSuccess(forgotResult: PersonInforResult) {
        Dialogs.showToastInformation(this,getString(R.string.update_person_info_success))
        isEditting = false
        txt_save_edit_shop_info.setText(getString(R.string.edit))
        enableEdit(false)
        prg_shop_info.visibility = View.GONE
    }

    override fun updateUserError(forgotResult: String) {
        prg_shop_info.visibility = View.GONE
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
                if(dataUserInfo!!.data.shop_district_id.toInt() == item.id){
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
                if(dataUserInfo!!.data.shop_commune_id.toInt() == item.id){
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
                if (dataUserInfo!!.data.shop_city_id.toInt() == item.id) {
                    txt_select_provice_and_city.setText(item.value)
                }
            }
        }
    }

    override fun instantiatePresenter(): ShopInfoPresenter {
        return ShopInfoPresenter(this)
    }

    var isEditting = false
    var isInfrontOfCitizenId = false
    var merchant_registration_front_site = ""
    var merchant_registration_back_site = ""
    var listSelectProvice : ArrayList<BottomPopupModel> = ArrayList()
    var listSelectDistrict : ArrayList<BottomPopupModel> = ArrayList()
    var listSelectWard : ArrayList<BottomPopupModel> = ArrayList()
    var city_id = ""
    var district_id = ""
    var commune_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_shop_info)
        layout_add_image_before_citizen_id.isEnabled = false
        layout_add_image_behind_citizen_id.isEnabled = false
        img_add_image_before_citizen_id!!.scaleType = ImageView.ScaleType.CENTER_CROP
        img_add_image_behind_citizen_id!!.scaleType = ImageView.ScaleType.CENTER_CROP

        edt_shop_name.setText(dataUserInfo!!.data.shop_name ?: "")
        edt_founding_day.setText(DateUtil.convertDateToStringBirthDay(dataUserInfo!!.data.shop_created ?: ""))
        edt_introduce.setText(dataUserInfo!!.data.description ?: "")
//        edt_address.setText(dataUserInfo!!.data.shop_address ?: "")

        txt_select_address.setText(dataUserInfo!!.data.shop_address)

        presenter!!.getCity(this!!,"query","")
        presenter.getDistrict(this,"query","",dataUserInfo!!.data.shop_city_id ?: "")
        presenter.getCommune(this,"query","",dataUserInfo!!.data.shop_district_id ?: "")

        for (item in masterData!!.data.categories){
            if(item.id.toString().equals(dataUserInfo!!.data.business_area)){
                edt_career.setText(item.title)
            }
        }

        merchant_registration_front_site = dataUserInfo!!.data.merchant_registration_front_site ?: ""
        merchant_registration_back_site = dataUserInfo!!.data.merchant_registration_back_site ?: ""
        txt_view_more_shop_info.setOnClickListener(View.OnClickListener {
            if(edt_introduce.maxLines == 4){
                edt_introduce.maxLines = 9999
                txt_view_more_shop_info.setText(this!!.getString(R.string.show_less))
            }else{
                edt_introduce.maxLines = 4
                txt_view_more_shop_info.setText(this!!.getString(R.string.view_more))
            }
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
        img_founding_day.setOnClickListener(View.OnClickListener {
            Offer.showDialogSelectCalendar(this,{ time:String, dialogNew: Dialog ->
                edt_founding_day.setText(time)
                dialogNew.dismiss()
            })
        })
        var listSelectOffer : ArrayList<BottomPopupModel> = ArrayList()
        var listSelectOfferId : ArrayList<Int> = ArrayList()
        for(item in masterData!!.data.categories){
            listSelectOffer.add(BottomPopupModel(item.id,item.title))
            listSelectOfferId.add(item.id)
        }
        var typeOffer : Int = 0
        edt_career!!.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listSelectOffer, getString(R.string.select_offer),{ dialogNew: Dialog, idSelect : Int ->
                edt_career.setText(listSelectOffer[idSelect].value.toString())
                typeOffer = listSelectOfferId[idSelect]
                dialogNew.dismiss()
            })
        })

        txt_save_edit_shop_info.setOnClickListener(View.OnClickListener {
            if(isEditting){
                var personInfoInput = PersonInfoInput(dataUserInfo!!.data.fullname ?: "",dataUserInfo!!.data.dob ?: "",dataUserInfo!!.data.fontside_image_identity_cart ?: ""
                    ,dataUserInfo!!.data.backside_image_identity_cart ?: "",
                    dataUserInfo!!.data.gender.toString() ?: "",dataUserInfo!!.data.city_id ?: "",
                    dataUserInfo!!.data.district_id ?: "",dataUserInfo!!.data.commune_id ?: "",dataUserInfo!!.data.address ?: "",
                    dataUserInfo!!.data.shop_created,edt_introduce.text.toString().trim(),txt_select_address.text.toString(),
                    typeOffer.toString(),dataUserInfo!!.data.avatar ?: "",merchant_registration_front_site, merchant_registration_back_site,
                    dataUserInfo!!.data.bank_account_number ?: "",dataUserInfo!!.data.bank_id ?: "",
                    city_id,district_id,commune_id)
                presenter!!.updatePersonInfo(this, prefs!!.getString(TOKEN,""),personInfoInput)
                prg_shop_info.visibility = View.VISIBLE
            }else{
                isEditting = true
                txt_save_edit_shop_info.setText(getString(R.string.save))
                enableEdit(true)
            }
        })

        img_back_shop_info.setOnClickListener(View.OnClickListener {
            finish()
        })

        CommonUtil.hideKeyboard(edt_shop_name,this@ShopInfoActivity)
    }

    fun enableEdit(enable : Boolean){
        if(enable){
            edt_shop_name.isEnabled = true
            edt_founding_day.isEnabled = true
            edt_edit_bussiness_number.isEnabled = true
            edt_introduce.isEnabled = true
            layout_add_image_before_citizen_id.isEnabled = true
            layout_add_image_before_citizen_id.visibility = View.VISIBLE
            layout_add_image_behind_citizen_id.isEnabled = true
            layout_add_image_behind_citizen_id.visibility = View.VISIBLE
            img_add_image_before_citizen_id.visibility = View.GONE
            img_add_image_behind_citizen_id.visibility = View.GONE
            txt_select_provice_and_city.isEnabled = true
            txt_select_district.isEnabled = true
            txt_select_ward.isEnabled = true
            txt_select_address.isEnabled = true
            edt_career.isEnabled = true
            img_founding_day.visibility = View.VISIBLE
        }else{
            edt_shop_name.isEnabled = false
            edt_founding_day.isEnabled = false
            edt_edit_bussiness_number.isEnabled = false
            edt_introduce.isEnabled = false
            layout_add_image_before_citizen_id.isEnabled = false
            layout_add_image_before_citizen_id.visibility = View.GONE
            layout_add_image_behind_citizen_id.isEnabled = false
            layout_add_image_behind_citizen_id.visibility = View.GONE
            img_add_image_before_citizen_id.visibility = View.VISIBLE
            img_add_image_behind_citizen_id.visibility = View.VISIBLE
            txt_select_provice_and_city.isEnabled = false
            txt_select_district.isEnabled = false
            txt_select_ward.isEnabled = false
            txt_select_address.isEnabled = false
            edt_career.isEnabled = false
            img_founding_day.visibility = View.GONE
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
                    prg_shop_info.visibility = View.VISIBLE
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
                        prg_shop_info.visibility = View.VISIBLE
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