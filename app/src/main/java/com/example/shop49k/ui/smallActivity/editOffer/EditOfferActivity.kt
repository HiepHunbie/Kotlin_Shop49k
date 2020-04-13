package com.example.shop49k.ui.smallActivity.editOffer

import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.shop49k.R
import com.example.shop49k.model.image.ImageEditOfferModel
import com.example.shop49k.ui.fragment.offer.ImageEditOfferAdapter
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.model.bottomPopup.BottomPopupModel
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.offer.AddNewData
import com.example.shop49k.model.offer.AddressDetail
import com.example.shop49k.model.offer.DataOfferDetail
import com.example.shop49k.model.offer.editOffer.OfferInput
import com.example.shop49k.model.offer.editOffer.OfferResult
import com.example.shop49k.ui.dialog.offer.DialogAddNewPlace
import com.example.shop49k.ui.fragment.offer.PlaceShopAdapter
import com.example.shop49k.utils.*
import com.example.shop49k.utils.`object`.*
import kotlinx.android.synthetic.main.dialog_edit_offer.*
import java.io.Serializable

class EditOfferActivity : BaseActivity<EditOfferPresenter>(),EditOfferView, Serializable {
    override fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectDistrict.clear()
        for(item in dataCityDetail){
            listSelectDistrict.add(AddNewData(item.id,item.text))
        }
    }

    override fun getCommueSuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectWard.clear()
        for(item in dataCityDetail){
            listSelectWard.add(AddNewData(item.id,item.text))
        }
    }

    override fun getCitySuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectProvice.clear()
        for(item in dataCityDetail){
            listSelectProvice.add(AddNewData(item.id,item.text))
        }
    }

    override fun successAddNewAddress(offerResult: OfferResult, dialog: Dialog?) {
    }

    override fun errorAddNewAddress(offerResult: String) {
        Dialogs.showToastErrorNewNotListenerClick(this!!,offerResult)
    }

    override fun successEditOffer(offerResult: OfferResult, dialog : Dialog?) {
        if(dialog!= null){
            dialog.dismiss()
        }
        Dialogs.showToastInformation(this,offerResult.message!!)
        finish()
    }

    override fun errorEditOffer(offerResult: String) {
        Dialogs.showToastErrorNewNotListenerClick(this!!,offerResult)
    }

    override fun instantiatePresenter(): EditOfferPresenter {
        return EditOfferPresenter(this)
    }
    var dataOfferDetail : DataOfferDetail? = null
    var title : String = ""
    var typeDialog = 0
    var listImage : ArrayList<ImageEditOfferModel> = ArrayList()
    var imageEditOfferAdapter : ImageEditOfferAdapter? = null
    var listPlace : ArrayList<AddressDetail> = ArrayList()
    var listSelectProvice : ArrayList<AddNewData> = ArrayList()
    var listSelectDistrict : ArrayList<AddNewData> = ArrayList()
    var listSelectWard : ArrayList<AddNewData> = ArrayList()
    var placeShopAdapter : PlaceShopAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit_offer)
        var bundle :Bundle ?=intent.extras
        dataOfferDetail = bundle!!.getParcelable<DataOfferDetail>(OFFER_DETAIL)
        title = bundle!!.getString(TITLE)
        typeDialog = bundle!!.getInt(TYPE_DIALOG)
        
        title_tab_offer!!.setText(title)

        var listSelectOffer : ArrayList<BottomPopupModel> = ArrayList()
        var listSelectOfferId : ArrayList<Int> = ArrayList()
        for(item in masterData!!.data.categories){
            listSelectOffer.add(BottomPopupModel(item.id,item.title))
            listSelectOfferId.add(item.id)
        }
        var typeOffer : Int = 0
        img_offer_type_edit_offer!!.setOnClickListener(View.OnClickListener {
            PersonInfoUtil.showDialogBottomPersonInfo(this, listSelectOffer, getString(R.string.select_offer),{ dialogNew: Dialog, idSelect : Int ->
                txt_select_type_edit_offer.setText(listSelectOffer[idSelect].value.toString())
                typeOffer = listSelectOfferId[idSelect]
                dialogNew.dismiss()
            })
        })

        if(dataOfferDetail!!.banner_image != null){
            if(dataOfferDetail!!.banner_image!!.isNotEmpty()){
                listImage.add(ImageEditOfferModel(dataOfferDetail!!.banner_image,null,0))
            }
        }
        if(dataOfferDetail!!.images != null){
            for(item in dataOfferDetail!!.images){
                listImage.add(ImageEditOfferModel(item.url,null,1))
            }
        }

        imageEditOfferAdapter = ImageEditOfferAdapter(this, { itemDto: ImageEditOfferModel, position: Int ->
            listImage.removeAt(position)
            imageEditOfferAdapter!!.updatePosts(listImage,false)

        })
        edt_title_edit_offer.setText(dataOfferDetail!!.title)
        txt_time_from_edit_offer.setText(DateUtil.convertDateToStringHasHour(dataOfferDetail!!.apply_start_time))
        txt_time_to_edit_offer.setText(DateUtil.convertDateToStringHasHour(dataOfferDetail!!.apply_end_time))
        ryv_image_edit_offer!!.adapter = imageEditOfferAdapter
        ryv_image_edit_offer!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imageEditOfferAdapter!!.updatePosts(listImage,false)

        if(dataOfferDetail != null){
            if(dataOfferDetail!!.status==0){
                layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_gray)
                txt_status_edit_offer!!.setTextColor(this.resources.getColor(R.color.text_hide))
                txt_status_edit_offer!!.setText(this.getString(R.string.pending))
            }else if(dataOfferDetail!!.status==1){
                layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_blue)
                txt_status_edit_offer!!.setTextColor(this.resources.getColor(R.color.text_blue))
                txt_status_edit_offer!!.setText(this.getString(R.string.running))
            }else if(dataOfferDetail!!.status==2){
                layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_green)
                txt_status_edit_offer!!.setTextColor(this.resources.getColor(R.color.text_green_home))
                txt_status_edit_offer!!.setText(this.getString(R.string.finish))
            }else if(dataOfferDetail!!.status==3){
                layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_red)
                txt_status_edit_offer!!.setTextColor(this.resources.getColor(R.color.button_login))
                txt_status_edit_offer!!.setText(this.getString(R.string.cancel))
            }

            txt_delete_edit_offer!!.visibility = View.VISIBLE
        }else{
            layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_gray)
            txt_status_edit_offer!!.setTextColor(this.resources.getColor(R.color.text_hide))
            txt_status_edit_offer!!.setText(this.getString(R.string.drap))

            txt_delete_edit_offer!!.visibility = View.GONE
        }

        if(dataOfferDetail!!.address != null){
            for(item in dataOfferDetail!!.address){
                listPlace.add(item)
            }
        }

        if (dataOfferDetail!!.discount_type.equals("1")){
            rdb_discount_percent_edit_offer.isChecked = true
            txt_discount_percent_value_edit_offer.setText(dataOfferDetail!!.discount_value.toString())
        }else if (dataOfferDetail!!.discount_type.equals("2")){
            rdb_discount_money_edit_offer.isChecked = true
            txt_discount_money_value_edit_offer.setText(dataOfferDetail!!.discount_value.toString())
        }
        txt_note_offer_edit_offer.setText(dataOfferDetail!!.discount_note)
        edt_guide_use_edit_offer.setText(dataOfferDetail!!.how_to_use)
        edt_conditions_apply_edit_offer.setText(dataOfferDetail!!.apply_condition)
        for(item in masterData!!.data.categories){
            if(item.id == dataOfferDetail!!.type){
                txt_select_type_edit_offer.setText(item.title)
                typeOffer = item.id
            }
        }
        txt_offer_count_edit_offer.setText(dataOfferDetail!!.total.toString())
        var count = dataOfferDetail!!.total - dataOfferDetail!!.used
        txt_left_offer_count_edit_offer.setText(count.toString())
        placeShopAdapter = PlaceShopAdapter(this!!)
        ryv_place_apply_edit_offer.adapter = placeShopAdapter
        ryv_place_apply_edit_offer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        placeShopAdapter!!.updatePosts(listPlace,false)

        rdb_discount_percent_edit_offer.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rdb_discount_money_edit_offer.isChecked = false
            }else{
                rdb_discount_money_edit_offer.isChecked = true
            }
        }
        rdb_discount_money_edit_offer.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                rdb_discount_percent_edit_offer.isChecked = false
            }else{
                rdb_discount_percent_edit_offer.isChecked = true
            }
        }

        var timeFrom = dataOfferDetail!!.apply_start_time
        var timeTo = dataOfferDetail!!.apply_end_time
        img_calendar_from.setOnClickListener(View.OnClickListener {
            Offer.showDialogSelectCalendar(this,{time:String,dialogNew: Dialog ->
                txt_time_from_edit_offer.setText(time)
                timeFrom =time
                dialogNew.dismiss()
            })
        })
        img_calendar_to.setOnClickListener(View.OnClickListener {
            Offer.showDialogSelectCalendar(this,{time:String,dialogNew: Dialog ->
                txt_time_to_edit_offer.setText(time)
                timeTo = time
                dialogNew.dismiss()
            })
        })
        txt_add_place_edit_offer.setOnClickListener(View.OnClickListener {
//            Offer.showDialogAddNewPlace(this,presenter!!,dataOfferDetail!!,{proviceSelect:AddNewData,
//                                                                                                  districtSelect:AddNewData,wardSelect:AddNewData,
//                                                                                                  numberStreet:String,dialogNew: Dialog,progressBar : ProgressBar ->
//                var listOfferAddress : ArrayList<Offer_address> = ArrayList()
//                listOfferAddress.add((Offer_address("add", listOf(Address(proviceSelect.id,proviceSelect.value,districtSelect.id,districtSelect.value,
//                    wardSelect.id,wardSelect.value,numberStreet,dataOfferDetail!!.id)))))
//                listOfferAddress.add((Offer_address("delete", listOf())))
//                listOfferAddress.add((Offer_address("update", listOf())))
//                var address = AddAddressInput(dataOfferDetail!!.id,listOfferAddress)
//                presenter.addNewAddress(prefs!!.getString(TOKEN, ""),address,progressBar,this,dialogNew)
//                listPlace.add(AddressDetail("",proviceSelect.id.toString(),proviceSelect.value,districtSelect.id.toString(),districtSelect.value,
//                    wardSelect.id.toString(),wardSelect.value,"",numberStreet,"",dataOfferDetail!!.id.toString()))
//                placeShopAdapter!!.updatePosts(listPlace,false)
//                dialogNew.dismiss()
//            },{ dialogNew: Dialog ->
//                dialogNew.dismiss()
//            })

            val newFragment = DialogAddNewPlace.newInstance(this,dataOfferDetail!!)
            newFragment.show(supportFragmentManager,"")
        })
        txt_save_edit_offer!!.setOnClickListener(View.OnClickListener {
            var id = dataOfferDetail!!.id
            var offer_number = txt_offer_count_edit_offer.text.toString().toInt()
            var offer_left = txt_left_offer_count_edit_offer.text.toString().toInt()
            var discount_type = 0
            var discount_value = ""
            var how_to_use = edt_guide_use_edit_offer.text.toString()
            var apply_condition = edt_conditions_apply_edit_offer.text.toString()
            var title = edt_title_edit_offer.text.toString()
            var discount_note = txt_note_offer_edit_offer.text.toString()
            if(rdb_discount_percent_edit_offer.isChecked){
                discount_type = 0
                discount_value = txt_discount_percent_value_edit_offer.text.toString()
            }else if(rdb_discount_money_edit_offer.isChecked){
                discount_type = 1
                discount_value = txt_discount_money_value_edit_offer.text.toString()
            }
            var offerInput = OfferInput(id,dataOfferDetail!!.status,timeFrom,timeTo,how_to_use,apply_condition,title,offer_number,
                offer_left,discount_type,discount_value,discount_note,typeOffer)

            presenter.editOffer(prefs!!.getString(TOKEN, ""),offerInput,prg_edit_offer,this, null)
        })

        img_back_edit_offer!!.setOnClickListener(View.OnClickListener {
            finish()
        })

        layout_add_image_edit_offer.setOnClickListener(View.OnClickListener {
            Dialogs.showDialogBottomSelectPicture(this!!,{ dialogNew: Dialog, pos : Int ->
                if(pos == 0){
                    GetImageUtil.captureImage(dataOfferDetail!!.id.toString().toString(),this)
                }else if(pos == 1){
                    GetImageUtil.openAlbum(this)
                }
                dialogNew.dismiss()
            })
        })

        txt_delete_edit_offer!!.setOnClickListener(View.OnClickListener {
            Dialogs.showDialogDeleteOffer(this,dataOfferDetail!!,{ dialogSmall: Dialog, itemDto : DataOfferDetail ->
                var id = dataOfferDetail!!.id
                var offer_number = txt_offer_count_edit_offer.text.toString().toInt()
                var offer_left = txt_left_offer_count_edit_offer.text.toString().toInt()
                var discount_type = 0
                var discount_value = ""
                var how_to_use = edt_guide_use_edit_offer.text.toString()
                var apply_condition = edt_conditions_apply_edit_offer.text.toString()
                var title = edt_title_edit_offer.text.toString()
                var discount_note = txt_note_offer_edit_offer.text.toString()
                if(rdb_discount_percent_edit_offer.isChecked){
                    discount_type = 1
                    discount_value = txt_discount_percent_value_edit_offer.text.toString()
                }else if(rdb_discount_money_edit_offer.isChecked){
                    discount_type = 2
                    discount_value = txt_discount_money_value_edit_offer.text.toString()
                }
                var offerInput = OfferInput(id,dataOfferDetail!!.status,timeFrom,timeTo,how_to_use,apply_condition,title,offer_number,
                    offer_left,discount_type,discount_value,discount_note,typeOffer)

                presenter.editOffer(prefs!!.getString(TOKEN, ""),offerInput,prg_edit_offer,this,dialogSmall)
//                dialogSmall.dismiss()
            })
        })
//        // Pick a style based on the num.
//        val style = DialogFragment.STYLE_NO_FRAME
//        val theme = R.style.DialogCustom
//        setStyle(style, theme)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GetImageUtil.FINAL_TAKE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(GetImageUtil.imageUri!!))
//                    picture!!.setImageBitmap(bitmap)
                    listImage.add(ImageEditOfferModel(null,bitmap,1))
                    imageEditOfferAdapter!!.updatePosts(listImage,false)
                }
            GetImageUtil.FINAL_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
//                        4.4以上
                        listImage.add(ImageEditOfferModel(GetImageUtil.handleImageOnKitkat(data,this)!!,null,1))
                        imageEditOfferAdapter!!.updatePosts(listImage,false)
                    }
                    else{
//                        4.4以下
                        GetImageUtil.handleImageBeforeKitkat(data)
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()
    }

}