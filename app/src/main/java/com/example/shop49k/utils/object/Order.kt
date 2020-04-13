package com.example.shop49k.utils.`object`

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Handler
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import com.example.shop49k.R
import com.example.shop49k.model.order.OrderDetail
import kotlinx.android.synthetic.main.dialog_order_detail.view.*
import kotlinx.android.synthetic.main.dialog_ship_id.view.*
import java.util.*
import kotlin.collections.ArrayList

object Order {
    fun showDialogOrderDetail(parentActivity: Activity, orderDetail: OrderDetail, clickBackListener: (Dialog) -> Unit
                              , clickViewShipIdListener: (Dialog,OrderDetail) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_order_detail, null)
        val mDialog = Dialog(parentActivity, R.style.DialogCustom)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()

        mDialogView.txt_order_detail_name.setText(orderDetail.title)
        if(orderDetail.status==0){
            mDialogView?.txt_ship_status!!.setTextColor(parentActivity.resources.getColor(R.color.orange))
            mDialogView?.txt_ship_status!!.setText(parentActivity.getString(R.string.shipping))
        }else if(orderDetail.status==1){
            mDialogView?.txt_ship_status!!.setTextColor(parentActivity.resources.getColor(R.color.text_green_home))
            mDialogView?.txt_ship_status!!.setText(parentActivity.getString(R.string.shipped))
        }else if(orderDetail.status==2){
            mDialogView?.txt_ship_status!!.setTextColor(parentActivity.resources.getColor(R.color.text_red_home))
            mDialogView?.txt_ship_status!!.setText(parentActivity.getString(R.string.canceled))
        }
        mDialogView.txt_order_date.setText(orderDetail.time)
        mDialogView.txt_ship_id.setText(orderDetail.id.toString())
        mDialogView.img_back_order.setOnClickListener(View.OnClickListener { clickBackListener(mDialog) })
        mDialogView.txt_view_ship_id.setOnClickListener(View.OnClickListener { clickViewShipIdListener(mDialog,orderDetail) })

        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }

    fun showDialogShowShipId(parentActivity: Activity, orderDetail: OrderDetail, clickBackListener: (Dialog) -> Unit){
        val mDialogView = parentActivity.layoutInflater.inflate(R.layout.dialog_ship_id, null)
        val mDialog = Dialog(parentActivity, R.style.DialogCustom)
        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.show()


        mDialogView.img_back_ship_id.setOnClickListener(View.OnClickListener { clickBackListener(mDialog) })

        mDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mDialog.dismiss()
            }
            false
        })
    }
}