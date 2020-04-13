package com.example.shop49k.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.ui.shipperMain.ShipperMainActivity
import com.example.shop49k.utils.OnBackPressed

abstract class BaseDialogFragment<P : com.example.shop49k.base.BasePresenter<com.example.shop49k.base.BaseView>>  : DialogFragment(),
    com.example.shop49k.base.MVPView, OnBackPressed {

    var parentActivity: MainActivity? = null
    var shipperMainActivity: ShipperMainActivity? = null
    private var progressDialog: ProgressDialog? = null
    open var presenter: P? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            val activity = context as MainActivity?
            this.parentActivity = activity
//            activity?.onFragmentAttached()
        }else if (context is ShipperMainActivity){
            val activity = context as ShipperMainActivity?
            this.shipperMainActivity = activity
        }
        this.retainInstance = retainInstance()
        presenter = instantiatePresenter()
    }

    private fun retainInstance(): Boolean {
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
//        performDependencyInjection()
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun hideProgress() {
        if (progressDialog != null && progressDialog?.isShowing!!) {
            progressDialog?.cancel()
        }
    }

    override fun showProgress() {
        hideProgress()
//        progressDialog = CommonUtil.showLoadingDialog(this.context)
    }

    fun getMainActivity() = parentActivity

    override fun onBackPressed() {}
    //    private fun performDependencyInjection() = AndroidSupportInjection.inject(this)
    protected abstract fun instantiatePresenter(): P

}