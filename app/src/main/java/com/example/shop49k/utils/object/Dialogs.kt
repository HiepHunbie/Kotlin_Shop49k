package com.example.shop49k.utils.`object`

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.shop49k.R
import com.example.shop49k.model.image.ImageDetailProfileModel
import com.example.shop49k.model.image.ImageModel
import com.example.shop49k.model.offer.AddressDetail
import com.example.shop49k.model.offer.DataOfferDetail
import com.example.shop49k.model.sale.DataSaleDetail
import com.example.shop49k.model.sale.SaleDetail
import com.example.shop49k.ui.fragment.offer.PlaceShopAdapter
import com.example.shop49k.ui.fragment.offer.SlidingImage_Adapter
import com.example.shop49k.ui.main.MainActivity
import kotlinx.android.synthetic.main.bottom_select_picture_dialog.view.*
import kotlinx.android.synthetic.main.bottom_sort_offer_dialog.view.*
import kotlinx.android.synthetic.main.bottom_sort_offer_dialog.view.img_close_popup
import kotlinx.android.synthetic.main.dialog_delete_image.view.*
import kotlinx.android.synthetic.main.dialog_delete_offer.view.*
import kotlinx.android.synthetic.main.dialog_error_custom.view.*
import kotlinx.android.synthetic.main.dialog_offer_detail.view.*
import kotlinx.android.synthetic.main.dialog_sale_detail.view.*
import kotlinx.android.synthetic.main.dialog_select_type_offer.view.*
import kotlinx.android.synthetic.main.dialog_success_otp.view.*
import kotlinx.android.synthetic.main.dialog_success_sign_up.view.*
import kotlinx.android.synthetic.main.dialog_view_iamge_detail.view.*
import kotlinx.android.synthetic.main.dialog_who_are_you.view.*
import kotlinx.android.synthetic.main.toast_custom_information.view.*
import java.util.*
import kotlin.collections.ArrayList

object Dialogs {

    fun showDialogSuccessSignUp(parentActivity: Activity, clickEditListener: (Dialog) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_success_sign_up, null)
        val mDialog = Dialog(parentActivity)
        mDialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        mDialogView.txt_done_success_signup.setOnClickListener {
            //            mDialog.dismiss()
            clickEditListener(mDialog)
        }
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogSuccessOtp(parentActivity: Activity, clickDoneListener: (Dialog) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_success_otp, null)
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

    fun showDialogDeleteOffer(parentActivity: Activity, dataOfferDetail: DataOfferDetail, clickDeleteListener: (Dialog,DataOfferDetail) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_delete_offer, null)
        val mDialog = Dialog(parentActivity)
        mDialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
        mDialogView.txt_cancel_delete_offer.setOnClickListener(View.OnClickListener {
            mDialog.dismiss()
        })
        mDialogView.txt_ok_delete_offer.setOnClickListener {
            //            mDialog.dismiss()
            clickDeleteListener(mDialog,dataOfferDetail)
        }
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogDeleteImage(parentActivity: Activity, imageDetailProfileModel: ImageDetailProfileModel, clickDeleteListener: (Dialog,ImageDetailProfileModel) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_delete_image, null)
        val mDialog = Dialog(parentActivity)
        mDialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
        mDialogView.txt_cancel_delete_image.setOnClickListener(View.OnClickListener {
            mDialog.dismiss()
        })
        mDialogView.txt_ok_delete_image.setOnClickListener {
            //            mDialog.dismiss()
            clickDeleteListener(mDialog,imageDetailProfileModel)
        }
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }


    fun showDialogWhoAreYou(parentActivity:Activity, clickDoneListener: (Dialog,Boolean) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_who_are_you, null)
        val mDialog = Dialog(parentActivity,R.style.DialogCustom)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        mDialogView.rdo_get_merchant.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mDialogView.rdo_get_shipper.setChecked(false)
            }else{
                mDialogView.rdo_get_shipper.setChecked(true)
            }
            mDialogView.txt_continue_who_are_you.setBackgroundResource(R.drawable.custom_button_login)
            mDialogView.txt_continue_who_are_you.setTextColor(parentActivity!!.resources.getColor(R.color.white))
        }

        mDialogView.rdo_get_shipper.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mDialogView.rdo_get_merchant.setChecked(false)
            }else{
                mDialogView.rdo_get_merchant.setChecked(true)
            }
            mDialogView.txt_continue_who_are_you.setBackgroundResource(R.drawable.custom_button_login)
            mDialogView.txt_continue_who_are_you.setTextColor(parentActivity!!.resources.getColor(R.color.white))
        }

        mDialogView.layout_merchant.setOnClickListener(View.OnClickListener {
            if (mDialogView.rdo_get_merchant.isChecked) {
                mDialogView.rdo_get_merchant.setChecked(false)
            }else{
                mDialogView.rdo_get_merchant.setChecked(true)
            }
        })
        mDialogView.layout_shipper.setOnClickListener(View.OnClickListener {
            if (mDialogView.rdo_get_shipper.isChecked) {
                mDialogView.rdo_get_shipper.setChecked(false)
            }else{
                mDialogView.rdo_get_shipper.setChecked(true)
            }
        })

        mDialogView.txt_continue_who_are_you.setOnClickListener(View.OnClickListener {
            if(!mDialogView.rdo_get_merchant.isChecked && !mDialogView.rdo_get_shipper.isChecked){

            }else{
                if(mDialogView.rdo_get_merchant.isChecked){
                    clickDoneListener(mDialog,true)
                }else{
                    clickDoneListener(mDialog,false)
                }
            }
        })

        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogSaleDetail(parentActivity:Activity, saleDetail: DataSaleDetail, clickBackListener: (Dialog) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_sale_detail, null)
        val mDialog = Dialog(parentActivity,R.style.DialogCustom)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

//        if(saleDetail.created_at != null){
//            mDialogView.txt_time_ago_sale_detail.setText(DateUtil.convertDateToStringHasHour(saleDetail.created_at))
//        }
//
//        mDialogView.txt_title_sale_detail.setText(saleDetail.title)
        mDialogView.webview_sale_detail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        if(saleDetail.url != null){
            mDialogView.webview_sale_detail.loadUrl(saleDetail.url)
        }

        mDialogView.img_back_sale_detail.setOnClickListener(View.OnClickListener {
            clickBackListener(mDialog)
        })
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogDataOfferDetail(parentActivity:Activity, dataOfferDetail: DataOfferDetail, listPlace : ArrayList<String>, clickBackListener: (Dialog) -> Unit
                                  , clickEditListener: (Dialog,DataOfferDetail) -> Unit, clickDeleteListener: (Dialog,DataOfferDetail) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_offer_detail, null)
        val mDialog = Dialog(parentActivity,R.style.DialogCustom)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        mDialogView.txt_title_offer_detail.setText(dataOfferDetail.title)
        if(dataOfferDetail.status==0){
            mDialogView.layout_status_offer_detail.setBackgroundResource(R.drawable.custom_status_offer_item_gray)
            mDialogView.txt_status_offer_detail.setTextColor(parentActivity.resources.getColor(R.color.text_hide))
            mDialogView.txt_status_offer_detail.setText(parentActivity.getString(R.string.pending))
        }else if(dataOfferDetail.status==1){
            mDialogView.layout_status_offer_detail.setBackgroundResource(R.drawable.custom_status_offer_item_blue)
            mDialogView.txt_status_offer_detail.setTextColor(parentActivity.resources.getColor(R.color.text_blue))
            mDialogView.txt_status_offer_detail.setText(parentActivity.getString(R.string.running))
        }else if(dataOfferDetail.status==2){
            mDialogView.layout_status_offer_detail.setBackgroundResource(R.drawable.custom_status_offer_item_green)
            mDialogView.txt_status_offer_detail.setTextColor(parentActivity.resources.getColor(R.color.text_green_home))
            mDialogView.txt_status_offer_detail.setText(parentActivity.getString(R.string.finish))
        }else if(dataOfferDetail.status==3){
            mDialogView.layout_status_offer_detail.setBackgroundResource(R.drawable.custom_status_offer_item_red)
            mDialogView.txt_status_offer_detail.setTextColor(parentActivity.resources.getColor(R.color.button_login))
            mDialogView.txt_status_offer_detail.setText(parentActivity.getString(R.string.cancel))
        }
//        mDialogView.txt_time_offer_detail.setText(dataOfferDetail.updated_time)
        var notUsed : Int = 0
        notUsed = dataOfferDetail.total - dataOfferDetail.used
        mDialogView.num_left_offer_count.setText(notUsed.toString() + "/")
        mDialogView.num_left_offer_all.setText(dataOfferDetail.total.toString())
        mDialogView.txt_time_offer_detail.setText(DateUtil.convertDateToStringOrgManagement(dataOfferDetail.apply_start_time ?: ""))
        mDialogView.txt_hour_offer_detail.setText(DateUtil.convertDateToStringOrgManagement(dataOfferDetail.apply_end_time?: ""))
        if(dataOfferDetail.discount_value != null){
            mDialogView.txt_sale_detail_title_offer_detail.setText(parentActivity.getString(R.string.diss_count) + " "+ dataOfferDetail.discount_value + "%")
        }
        if(dataOfferDetail.discount_note != null){
            mDialogView.txt_sale_detail_offer_detail.setText(dataOfferDetail.discount_note)
        }
        mDialogView.txt_delete_offer_detail.setOnClickListener(View.OnClickListener { clickDeleteListener(mDialog,dataOfferDetail) })
        mDialogView.txt_edit_offer_detail.setOnClickListener(View.OnClickListener { clickEditListener(mDialog,dataOfferDetail) })

        // Slide
        var imageModelArrayList: ArrayList<ImageModel>? = ArrayList()
         var mPager: ViewPager? = null
         var currentPage = 0
         var NUM_PAGES = 0

//        imageModelArrayList!!.add(ImageModel(R.drawable.ic_back_1_sale_detail))
//        imageModelArrayList!!.add(ImageModel(R.drawable.ic_back_2_sale_detail))
//        imageModelArrayList!!.add(ImageModel(R.drawable.ic_back_top_home_orange))
//        imageModelArrayList!!.add(ImageModel(R.drawable.ic_back_top_home_hide))

        if(dataOfferDetail.images != null){
            for (item in dataOfferDetail.images){
                imageModelArrayList!!.add(ImageModel(item!!.url))
            }
        }

        mDialogView.pager_offer_detail.adapter = SlidingImage_Adapter(parentActivity!!, imageModelArrayList!!)

        mDialogView.indicator_offer_detail.setViewPager(mDialogView.pager_offer_detail)

        mDialogView.txt_using_offer_detail.setText(dataOfferDetail.how_to_use)
        mDialogView.txt_conditions_apply_offer_detail.setText(dataOfferDetail.apply_condition)

        val density = parentActivity!!.resources.displayMetrics.density

        //Set circle indicator radius
        mDialogView.indicator_offer_detail.setRadius(5 * density)

        NUM_PAGES = imageModelArrayList!!.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mDialogView.pager_offer_detail!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

        // Pager listener over indicator
        mDialogView.indicator_offer_detail.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                currentPage = position

            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {

            }

            override fun onPageScrollStateChanged(pos: Int) {

            }
        })

        // place list

        var listPlaceData : ArrayList<AddressDetail> = ArrayList()
        if (dataOfferDetail.address != null){
            for (item in dataOfferDetail.address){
                listPlaceData.add(item)
            }
        }
        var placeShopAdapter : PlaceShopAdapter? = null
        placeShopAdapter = PlaceShopAdapter(parentActivity!!)
        mDialogView.ryv_place_shop_offer_detail!!.adapter = placeShopAdapter
        mDialogView.ryv_place_shop_offer_detail!!.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)
        placeShopAdapter!!.updatePosts(listPlaceData,false)

        mDialogView.txt_view_more_offer_detail.setOnClickListener(View.OnClickListener {
            if(mDialogView.txt_conditions_apply_offer_detail.maxLines == 4){
                mDialogView.txt_conditions_apply_offer_detail.maxLines = 9999
                mDialogView.txt_view_more_offer_detail.setText(parentActivity!!.getString(R.string.show_less))
            }else{
                mDialogView.txt_conditions_apply_offer_detail.maxLines = 4
                mDialogView.txt_view_more_offer_detail.setText(parentActivity!!.getString(R.string.view_more))
            }

        })
        mDialogView.img_back_offer_detail.setOnClickListener(View.OnClickListener {
            clickBackListener(mDialog)
        })
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogBottomSelectPicture(parentActivity: Activity,clickItemListener: (Dialog,Int) -> Unit){
        val view = parentActivity.layoutInflater.inflate(R.layout.bottom_select_picture_dialog, null)
        val mBuilder = AlertDialog.Builder(parentActivity,R.style.DialogCustom).setView(view)
        //show dialog
        val  mAlertDialog = mBuilder.show()
        var sort : Int = 0
//        mAlertDialog.setCancelable(false)
//        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.getWindow().setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
//        val dialog = BottomSheetDialog(parentActivity)
//        val dialog = Dialog(parentActivity,R.style.DialogCustomNotFull)
//        dialog.setContentView(view)
        mAlertDialog.show()
       view.img_close_popup_picture.setOnClickListener(View.OnClickListener {
           mAlertDialog.dismiss()
       })

        view.layout_capture_image.setOnClickListener(View.OnClickListener {
            clickItemListener(mAlertDialog,0)
            mAlertDialog.dismiss()
        })
        view.layout_get_picture_from_gallery.setOnClickListener(View.OnClickListener {
            clickItemListener(mAlertDialog,1)
            mAlertDialog.dismiss()
        })
        mAlertDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mAlertDialog.dismiss()
            }
            false
        })

    }


    fun showDialogBottomSortOffer(parentActivity: MainActivity,idSort : Int,title:String, clickItemListener: (Dialog,Int) -> Unit){
        val view = parentActivity.layoutInflater.inflate(R.layout.bottom_sort_offer_dialog, null)
        val mBuilder = AlertDialog.Builder(parentActivity,R.style.DialogCustom).setView(view)
        //show dialog
        val  mAlertDialog = mBuilder.show()
        var sort : Int = 0
//        mAlertDialog.setCancelable(false)
//        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.getWindow().setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
//        val dialog = BottomSheetDialog(parentActivity)
//        val dialog = Dialog(parentActivity,R.style.DialogCustomNotFull)
//        dialog.setContentView(view)
        mAlertDialog.show()

        view.txt_title.setText(title)
        view.img_close_popup.setOnClickListener(View.OnClickListener {
            mAlertDialog.dismiss()
        })

        if(idSort == 0){
            view.layout_all_sort_offer.setBackgroundResource(R.color.background_main)
            view.layout_new_sort_offer.setBackgroundResource(R.color.white)
            view.layout_by_name_sort_offer.setBackgroundResource(R.color.white)
            view.chk_all_sort_offer.isChecked = true
            sort = 0
        }else if(idSort == 1){
            view.layout_all_sort_offer.setBackgroundResource(R.color.white)
            view.layout_new_sort_offer.setBackgroundResource(R.color.background_main)
            view.layout_by_name_sort_offer.setBackgroundResource(R.color.white)
            view.chk_new_sort_offer.isChecked = true
            sort = 1
        }else if(idSort == 2){
            view.layout_all_sort_offer.setBackgroundResource(R.color.white)
            view.layout_new_sort_offer.setBackgroundResource(R.color.white)
            view.layout_by_name_sort_offer.setBackgroundResource(R.color.background_main)
            view.chk_by_name_sort_offer.isChecked = true
            sort = 2
        }
        view.layout_all_sort_offer.setOnClickListener(View.OnClickListener {
            view.layout_all_sort_offer.setBackgroundResource(R.color.background_main)
            view.layout_new_sort_offer.setBackgroundResource(R.color.white)
            view.layout_by_name_sort_offer.setBackgroundResource(R.color.white)
            view.chk_all_sort_offer.isChecked = true
            sort = 0
            clickItemListener(mAlertDialog,sort)
            mAlertDialog.dismiss()
        })
        view.layout_new_sort_offer.setOnClickListener(View.OnClickListener {
            view.layout_all_sort_offer.setBackgroundResource(R.color.white)
            view.layout_new_sort_offer.setBackgroundResource(R.color.background_main)
            view.layout_by_name_sort_offer.setBackgroundResource(R.color.white)
            view.chk_new_sort_offer.isChecked = true
            sort = 1
            clickItemListener(mAlertDialog,sort)
            mAlertDialog.dismiss()
        })
        view.layout_by_name_sort_offer.setOnClickListener(View.OnClickListener {
            view.layout_all_sort_offer.setBackgroundResource(R.color.white)
            view.layout_new_sort_offer.setBackgroundResource(R.color.white)
            view.layout_by_name_sort_offer.setBackgroundResource(R.color.background_main)
            view.chk_by_name_sort_offer.isChecked = true
            sort = 2
            clickItemListener(mAlertDialog,sort)
            mAlertDialog.dismiss()
        })

        view.chk_all_sort_offer.setOnClickListener(View.OnClickListener {
            view.layout_all_sort_offer.callOnClick()
        })
        view.chk_new_sort_offer.setOnClickListener(View.OnClickListener {
            view.layout_new_sort_offer.callOnClick()
        })
        view.chk_by_name_sort_offer.setOnClickListener(View.OnClickListener {
            view.layout_by_name_sort_offer.callOnClick()
        })
        mAlertDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mAlertDialog.dismiss()
            }
            false
        })

    }


    fun showDialogSelectTypeOffer(parentActivity: Activity, clickItemListener: (Dialog, Int) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_select_type_offer, null)
        val mBuilder = AlertDialog.Builder(parentActivity,R.style.DialogCustom).setView(mDialogView)
        //show dialog
        val  mDialog = mBuilder.show()
        mDialog.getWindow().setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        mDialog.show()
//        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_select_type_offer, null)
//        val mDialog = Dialog(parentActivity)
//        mDialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        mDialog.setContentView(mDialogView)
////        mDialog.setCancelable(false)
////        mDialog.setCanceledOnTouchOutside(false)
//        mDialog.show()

        mDialogView.rdb_fashion.setOnClickListener {
            //            mDialog.dismiss()
            clickItemListener(mDialog,0)
        }
        mDialogView.rdb_service.setOnClickListener {
            //            mDialog.dismiss()
            clickItemListener(mDialog,1)
        }
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogViewImageDetail(parentActivity:Activity, imageDetailProfileModel: ImageDetailProfileModel, clickDeleteListener: (Dialog,ImageDetailProfileModel) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_view_iamge_detail, null)
        val mDialog = Dialog(parentActivity,R.style.DialogCustom)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        if(imageDetailProfileModel.image_drawable != null){
            Glide.with(parentActivity).load(imageDetailProfileModel.image_drawable).into(mDialogView.img_image_view_detail)
        }else{
            mDialogView.img_image_view_detail.setImageBitmap(imageDetailProfileModel.bitmap)
        }

        mDialogView.img_delete_image.setOnClickListener(View.OnClickListener {
            clickDeleteListener(mDialog,imageDetailProfileModel)
        })
        mDialogView.img_view_back_image_detai.setOnClickListener(View.OnClickListener {
            mDialog.dismiss()
        })
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showToastErrorNewNotListenerClick(parentActivity:Activity,mess:String){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_error_custom, null)
        val mDialog = Dialog(parentActivity)
        mDialog.getWindow().setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        mDialogView.txt_mess_error.text = mess
        mDialogView.txt_ok.setOnClickListener {
            mDialog.dismiss()
        }
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showToastInformation(parentActivity:Activity,mess:String){
        val toastView = parentActivity.layoutInflater.inflate(R.layout.toast_custom_information, null)
        toastView.customToastText.text = mess

        val toast = Toast(parentActivity)
        toast.view = toastView
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.BOTTOM, 0, 100)
        toast.view = toastView
        toast.show()

        toastView.customToastDelete.setOnClickListener({
        })
    }

}