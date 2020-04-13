package com.example.shop49k.utils.`object`

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.shop49k.R
import kotlinx.android.synthetic.main.dialog_input_qrcode_by_hand.view.*

object QrCodeUtil {
    fun showDialogBottomQrCodeByHand(parentActivity: Activity, clickDoneListener: (Dialog, String) -> Unit){
        val view = parentActivity.layoutInflater.inflate(R.layout.dialog_input_qrcode_by_hand, null)
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

        view.edt_qr_code.setOnEditorActionListener(object: TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH ||
                            actionId == EditorInfo.IME_ACTION_DONE ||
                            (event != null &&
                                    event.action === KeyEvent.ACTION_DOWN &&
                                    event.keyCode === KeyEvent.KEYCODE_ENTER)))
                {
                    if (event == null || !event.isShiftPressed)
                    {
                        if(view.edt_qr_code.text.toString().trim().isNotEmpty()){
                            clickDoneListener(mAlertDialog, view.edt_qr_code.text.toString().trim())
                        }
                        return true // consume.
                    }
                }
                return false
            }
        })

        mAlertDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mAlertDialog.dismiss()
            }
            false
        })

    }

}