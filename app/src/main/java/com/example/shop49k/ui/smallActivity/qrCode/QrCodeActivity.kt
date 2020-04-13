package com.example.shop49k.ui.smallActivity.qrCode

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.utils.`object`.QrCodeUtil
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.dialog_person_info.*
import kotlinx.android.synthetic.main.dialog_qrcode.*

class QrCodeActivity : BaseActivity<QrCodePresenter>(), QrCodeView {

    override fun instantiatePresenter(): QrCodePresenter {
        return QrCodePresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_qrcode)

        txt_input_code_by_hand.setOnClickListener(View.OnClickListener {
            QrCodeUtil.showDialogBottomQrCodeByHand(this,{ dialogNew: Dialog, qrCode : String ->
                dialogNew.dismiss()
            })
        })

        txt_input_code_by_camera.setOnClickListener(View.OnClickListener {
            run {
                IntentIntegrator(this).setOrientationLocked(false).setPrompt("").initiateScan();
            }
        })

        img_close_scan_qr_code.setOnClickListener(View.OnClickListener {
            layout_result_qr_code.visibility = View.GONE
        })

        txt_get_order.setOnClickListener(View.OnClickListener {
            finish()
        })
        img_back_qr_code.setOnClickListener(View.OnClickListener { finish() })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){

            if(result.contents != null){
                layout_result_qr_code.visibility = View.VISIBLE
                txt_title_qr_code.text = result.contents
            } else {
                layout_result_qr_code.visibility = View.GONE
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}