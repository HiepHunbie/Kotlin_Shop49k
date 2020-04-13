package com.example.shop49k.ui.smallActivity.createOffer

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.model.bottomPopup.BottomPopupModel
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.image.ImageEditOfferModel
import com.example.shop49k.model.offer.AddressDetail
import com.example.shop49k.model.offer.createOffer.OfferCreateInput
import com.example.shop49k.model.offer.createOffer.OfferCreateResult
import com.example.shop49k.model.offer.editOffer.OfferInput
import com.example.shop49k.model.offer.editOffer.OfferResult
import com.example.shop49k.ui.dialog.createOffer.DialogAddNewPlaceCreateOffer
import com.example.shop49k.ui.dialog.offer.DialogAddNewPlace
import com.example.shop49k.ui.fragment.offer.ImageEditOfferAdapter
import com.example.shop49k.ui.fragment.offer.PlaceShopAdapter
import com.example.shop49k.utils.TOKEN
import com.example.shop49k.utils.`object`.Dialogs
import com.example.shop49k.utils.`object`.GetImageUtil
import com.example.shop49k.utils.`object`.Offer
import com.example.shop49k.utils.`object`.PersonInfoUtil
import kotlinx.android.synthetic.main.dialog_create_offer.*
import kotlinx.android.synthetic.main.dialog_create_offer.edt_conditions_apply_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.edt_guide_use_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.edt_title_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.img_back_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.img_calendar_from
import kotlinx.android.synthetic.main.dialog_create_offer.img_calendar_to
import kotlinx.android.synthetic.main.dialog_create_offer.img_offer_type_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.layout_add_image_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.layout_status_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.rdb_discount_money_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.rdb_discount_percent_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.ryv_image_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.ryv_place_apply_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.txt_add_place_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.txt_discount_money_value_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.txt_discount_percent_value_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.txt_left_offer_count_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.txt_offer_count_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.txt_save_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.txt_select_type_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.txt_status_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.txt_time_from_edit_offer
import kotlinx.android.synthetic.main.dialog_create_offer.txt_time_to_edit_offer
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CreateOfferActivity : BaseActivity<CreateOfferPresenter>(), CreateOfferView , Serializable {
    override fun successCreateOffer(offerResult: OfferCreateResult, dialog: Dialog?) {
        if(dialog!= null){
            dialog.dismiss()
        }
        Dialogs.showToastInformation(this,offerResult.message!! ?: "")
        finish()
    }

    override fun errorCreateOffer(offerResult: String) {
        Dialogs.showToastErrorNewNotListenerClick(this!!,offerResult)
    }

    override fun successAddNewAddress(offerResult: OfferResult, dialog: Dialog?) {
    }

    override fun errorAddNewAddress(offerResult: String) {
        Dialogs.showToastErrorNewNotListenerClick(this!!,offerResult)
    }

    override fun getCitySuccess(dataCityDetail: List<DataCityDetail>) {
    }

    override fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>) {
    }

    override fun getCommueSuccess(dataCityDetail: List<DataCityDetail>) {
    }

    override fun instantiatePresenter(): CreateOfferPresenter {
        return CreateOfferPresenter(this)
    }
    var title : String = ""
    var typeDialog = 0
    var listImage : ArrayList<ImageEditOfferModel> = ArrayList()
    var imageEditOfferAdapter : ImageEditOfferAdapter? = null
    var listPlace : ArrayList<AddressDetail> = ArrayList()
    var listSelectProvice : ArrayList<String> = ArrayList()
    var listSelectDistrict : ArrayList<String> = ArrayList()
    var listSelectWard : ArrayList<String> = ArrayList()
    var placeShopAdapter : PlaceShopAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_create_offer)

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

        listSelectProvice.add("Ha Noi")
        listSelectProvice.add("TP Ho Chi Minh")
        listSelectProvice.add("Da Nang")

        listSelectDistrict.add("Dong Da")
        listSelectDistrict.add("Thanh Xuan")
        listSelectDistrict.add("Cau Giay")

        listSelectWard.add("Tu Liem")
        listSelectWard.add("Lang Thuong")
        listSelectWard.add("Dich Vong")

        txt_add_place_edit_offer.setOnClickListener(View.OnClickListener {
//            Offer.showDialogAddNewPlace(this,listSelectProvice,listSelectDistrict,listSelectWard,{proviceSelect:String,
//                                                                                                  districtSelect:String,wardSelect:String,
//                                                                                                  numberStreet:String,dialogNew: Dialog ->
//                var address = numberStreet + " , "+ wardSelect+ " , "+ districtSelect+ " , "+ proviceSelect
//                listPlace.add(address)
//                placeShopAdapter!!.updatePosts(listPlace,false)
//                dialogNew.dismiss()
//            },{ dialogNew: Dialog ->
//                dialogNew.dismiss()
//            })
            val newFragment = DialogAddNewPlaceCreateOffer.newInstance(this)
            newFragment.show(supportFragmentManager,"")
        })
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm")
        var timeFrom = sdf.format(Date()).toString()
        var timeTo = sdf.format(Date()).toString()
        img_calendar_from.setOnClickListener(View.OnClickListener {
            Offer.showDialogSelectCalendar(this,{ time:String, dialogNew: Dialog ->
                txt_time_from_edit_offer.setText(time)
                timeFrom =time
                dialogNew.dismiss()
            })
        })
        img_calendar_to.setOnClickListener(View.OnClickListener {
            Offer.showDialogSelectCalendar(this,{ time:String, dialogNew: Dialog ->
                txt_time_to_edit_offer.setText(time)
                timeTo = time
                dialogNew.dismiss()
            })
        })
//        listImage.add(ImageEditOfferModel(R.drawable.ic_back_1_sale_detail,0))
//        listImage.add(ImageEditOfferModel(R.drawable.ic_back_2_sale_detail,1))
        imageEditOfferAdapter = ImageEditOfferAdapter(this, { itemDto: ImageEditOfferModel, position: Int ->
            listImage.removeAt(position)
            imageEditOfferAdapter!!.updatePosts(listImage,false)

        })
        ryv_image_edit_offer!!.adapter = imageEditOfferAdapter
        ryv_image_edit_offer!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imageEditOfferAdapter!!.updatePosts(listImage,false)
        layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_gray)
        txt_status_edit_offer!!.setTextColor(this.resources.getColor(R.color.text_hide))
        txt_status_edit_offer!!.setText(this.getString(R.string.drap))

        txt_save_edit_offer!!.setOnClickListener(View.OnClickListener {
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
            var offerInput = OfferCreateInput(0,timeFrom,timeTo,how_to_use,apply_condition,title,offer_number,
                offer_left,discount_type,discount_value,discount_note,typeOffer)
            presenter.createOffer(prefs!!.getString(TOKEN, ""),offerInput,prg_create_offer,this, null)
        })

        img_back_edit_offer!!.setOnClickListener(View.OnClickListener {
            finish()
        })

        layout_add_image_edit_offer.setOnClickListener(View.OnClickListener {
            Dialogs.showDialogBottomSelectPicture(this!!,{ dialogNew: Dialog, pos : Int ->
                if(pos == 0){
                    GetImageUtil.captureImage(listImage.size.toString(),this)
                }else if(pos == 1){
                    GetImageUtil.openAlbum(this)
                }
                dialogNew.dismiss()
            })
        })
//        // Pick a style based on the num.
//        val style = DialogFragment.STYLE_NO_FRAME
//        val theme = R.style.DialogCustom
//        setStyle(style, theme)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 ->
                if (grantResults.isNotEmpty() && grantResults.get(0) == PackageManager.PERMISSION_GRANTED){
                    GetImageUtil.openAlbum(this)
                }
                else {
//                    Toast.makeText(this, "You deied the permission", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GetImageUtil.FINAL_TAKE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val dir = Environment.getExternalStorageDirectory().toString()
                    val bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(GetImageUtil.imageUri!!))

                    listImage.add(ImageEditOfferModel(null,bitmap,1))
                    if(listImage.size>0){
                        ryv_image_edit_offer.visibility = View.VISIBLE
                    }else{
                        ryv_image_edit_offer.visibility = View.GONE
                    }
                    imageEditOfferAdapter!!.updatePosts(listImage,false)
                }
            GetImageUtil.FINAL_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
//                        4.4以上
                        listImage.add(ImageEditOfferModel(GetImageUtil.handleImageOnKitkat(data,this)!!,null,1))
                        imageEditOfferAdapter!!.updatePosts(listImage,false)
                        if(listImage.size>0){
                            ryv_image_edit_offer.visibility = View.VISIBLE
                        }else{
                            ryv_image_edit_offer.visibility = View.GONE
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