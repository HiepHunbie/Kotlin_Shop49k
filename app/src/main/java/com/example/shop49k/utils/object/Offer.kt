package com.example.shop49k.utils.`object`

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import com.example.shop49k.R
import com.example.shop49k.model.offer.AddNewData
import com.example.shop49k.model.offer.DataOfferDetail
import com.example.shop49k.ui.smallActivity.editOffer.EditOfferActivity
import com.example.shop49k.ui.smallActivity.editOffer.EditOfferPresenter
import com.example.shop49k.ui.smallActivity.editOffer.EditOfferView
import kotlinx.android.synthetic.main.dialog_add_new_place.view.*
import kotlinx.android.synthetic.main.dialog_select_calendar.view.*

object Offer {

    fun showDialogAddNewPlace(parentActivity: EditOfferActivity,presenter: EditOfferPresenter,offerDetail: DataOfferDetail, clickAddPlaceListener: (AddNewData,AddNewData,AddNewData,String,Dialog,ProgressBar) -> Unit
                              , clickCanceltListener: (Dialog) -> Unit) {
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_add_new_place, null)
        val mDialog = Dialog(parentActivity)
        mDialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
        var proviceSelect : AddNewData? = parentActivity!!.listSelectProvice[0]
        var districtSelect : AddNewData? = parentActivity.listSelectDistrict[0]
        var wardSelect : AddNewData? = parentActivity.listSelectWard[0]

        var listProvince : ArrayList<String> = ArrayList()
        var listDistrict : ArrayList<String> = ArrayList()
        var listWard : ArrayList<String> = ArrayList()
        for(item in parentActivity!!.listSelectProvice){
            listProvince.add(item.value)
        }
        for(item in parentActivity!!.listSelectDistrict){
            listDistrict.add(item.value)
        }
        for(item in parentActivity!!.listSelectWard){
            listWard.add(item.value)
        }
        val arrayAdapterProvince = ArrayAdapter(parentActivity, R.layout.item_spinner, listProvince )
        mDialogView.spn_add_place_provice_and_city!!.adapter = arrayAdapterProvince
        val arrayAdapterDistrict = ArrayAdapter(parentActivity, R.layout.item_spinner, listDistrict )
        mDialogView.spn_add_place_district!!.adapter = arrayAdapterDistrict
        val arrayAdapterWard = ArrayAdapter(parentActivity, R.layout.item_spinner, listWard )
        mDialogView.spn_add_place_ward!!.adapter = arrayAdapterWard
        mDialogView.spn_add_place_provice_and_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(position>-1){
                    proviceSelect = parentActivity!!.listSelectProvice[position]
                presenter.getDistrict(parentActivity,"query","",proviceSelect!!.id.toString())}
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        mDialogView.spn_add_place_district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(position>-1){
                    districtSelect = parentActivity.listSelectDistrict[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        mDialogView.spn_add_place_ward.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(position>-1){
                    wardSelect = parentActivity.listSelectWard[position]}
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        mDialogView.txt_done_add_new_place.setOnClickListener {
            clickAddPlaceListener(proviceSelect!!,districtSelect!!,wardSelect!!,mDialogView.edt_your_number_home_and_street_add_new_place.text.toString(),mDialog,mDialogView.prg_add_new_place)
        }
        mDialogView.txt_cancel_add_new_place.setOnClickListener {
            clickCanceltListener(mDialog)
        }
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogSelectCalendar(parentActivity: Activity, clickDoneListener: (String,Dialog) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_select_calendar, null)
        val mDialog = Dialog(parentActivity)
        mDialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()
        var time = ""
        var hourSet = ""

        mDialogView.time_picker_select.setOnTimeChangedListener { _, hour, minute -> var hour = hour
//            var am_pm = ""
//            // AM_PM decider logic
//            when {hour == 0 -> { hour += 12
//                am_pm = "AM"
//            }
//                hour == 12 -> am_pm = "PM"
//                hour > 12 -> { hour -= 12
//                    am_pm = "PM"
//                }
//                else -> am_pm = "AM"
//            }
            hourSet = hour.toString() + ":"+ minute.toString()
        }
        mDialogView.calendar_select?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
            time = ""+ year + "-" + (month + 1) + "-" + dayOfMonth
        }
        mDialogView.txt_done_select_calendar.setOnClickListener {
            clickDoneListener((time+ " "+hourSet).trim(),mDialog)
        }
        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }
}