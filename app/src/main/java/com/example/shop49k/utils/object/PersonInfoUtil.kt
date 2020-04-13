package com.example.shop49k.utils.`object`

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import com.example.shop49k.R
import com.example.shop49k.model.bottomPopup.BottomPopupModel
import com.example.shop49k.ui.fragment.offer.PlaceShopAdapter
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.ui.smallActivity.personInfo.PopupBottomAdapter
import kotlinx.android.synthetic.main.dialog_bottom_popup_person_info.view.*

object PersonInfoUtil {

    fun showDialogBottomPersonInfo(parentActivity: Activity, listData : ArrayList<BottomPopupModel>, title : String, clickItemListener: (Dialog, Int) -> Unit){
        val view = parentActivity.layoutInflater.inflate(R.layout.dialog_bottom_popup_person_info, null)
        val mBuilder = AlertDialog.Builder(parentActivity, R.style.DialogCustom).setView(view)
        //show dialog
        val  mAlertDialog = mBuilder.show()
        var sort : Int = 0
//        mAlertDialog.setCancelable(false)
//        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        val dialog = BottomSheetDialog(parentActivity)
//        val dialog = Dialog(parentActivity,R.style.DialogCustomNotFull)
//        dialog.setContentView(view)
        mAlertDialog.show()
        view.img_close_popup.setOnClickListener(View.OnClickListener {
            mAlertDialog.dismiss()
        })

        view.txt_title_popup.setText(title)

        var popupBottomAdapter : PopupBottomAdapter? = null
        popupBottomAdapter = PopupBottomAdapter(parentActivity!!, { itemDto: BottomPopupModel, position: Int ->
            clickItemListener(mAlertDialog,position)
        })
        view.ryv_popup_bottom!!.adapter = popupBottomAdapter
        view.ryv_popup_bottom!!.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)
        popupBottomAdapter!!.updatePosts(listData,false)
        mAlertDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mAlertDialog.dismiss()
            }
            false
        })

    }
}