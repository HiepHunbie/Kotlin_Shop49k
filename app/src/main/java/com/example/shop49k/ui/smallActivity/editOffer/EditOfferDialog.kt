package com.example.shop49k.ui.smallActivity.editOffer

import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.shop49k.R
import com.example.shop49k.base.BaseDialogFragment
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.image.ImageEditOfferModel
import com.example.shop49k.model.offer.DataOfferDetail
import com.example.shop49k.model.offer.editOffer.OfferResult
import com.example.shop49k.ui.fragment.offer.ImageEditOfferAdapter
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.utils.`object`.Dialogs
import kotlinx.android.synthetic.main.dialog_edit_offer.view.*

class EditOfferDialog : BaseDialogFragment<EditOfferPresenter>(),EditOfferView{
    override fun successAddNewAddress(offerResult: OfferResult, dialog: Dialog?) {
    }

    override fun errorAddNewAddress(offerResult: String) {
    }

    override fun getCitySuccess(dataCityDetail: List<DataCityDetail>) {
    }

    override fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>) {
    }

    override fun getCommueSuccess(dataCityDetail: List<DataCityDetail>) {
    }

    override fun successEditOffer(offerResult: OfferResult, dialog: Dialog?) {
    }

    override fun errorEditOffer(offerResult: String) {
    }

    override fun instantiatePresenter(): EditOfferPresenter {
        return EditOfferPresenter(this)
    }

    override fun getContext(): Context {
        return parentActivity!!.getContext()
    }
    var parentActivityGet : MainActivity? = null
    var dataOfferDetail : DataOfferDetail? = null
    var title : String = ""
    var typeDialog = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.parentActivityGet = arguments!!.getSerializable("parentActivity") as MainActivity?
        this.dataOfferDetail = arguments!!.getParcelable<DataOfferDetail>("DataOfferDetail")
        this.title = arguments!!.getString("title")
        this.typeDialog = arguments!!.getInt("typeDialog")

        // Pick a style based on the num.
        val style = DialogFragment.STYLE_NO_FRAME
        val theme = R.style.DialogCustom
        setStyle(style, theme)
    }

    var title_tab_offer : TextView? = null
    var ryv_image_edit_offer : RecyclerView? = null
    var listImage : ArrayList<ImageEditOfferModel> = ArrayList()
    var layout_status_edit_offer:RelativeLayout? = null
    var txt_status_edit_offer : TextView? = null
    var txt_save_edit_offer :TextView? = null
    var img_back_edit_offer : ImageView? = null
    var img_offer_type_edit_offer:ImageView? = null
    var txt_select_type_edit_offer : TextView? = null
    var txt_delete_edit_offer : TextView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.dialog_edit_offer, container, false)

        title_tab_offer = view.title_tab_offer
        ryv_image_edit_offer = view.ryv_image_edit_offer
        layout_status_edit_offer = view.layout_status_edit_offer
        txt_status_edit_offer = view.txt_status_edit_offer
        txt_save_edit_offer = view.txt_save_edit_offer
        img_back_edit_offer = view.img_back_edit_offer
        img_offer_type_edit_offer = view.img_offer_type_edit_offer
        txt_select_type_edit_offer = view.txt_select_type_edit_offer
        txt_delete_edit_offer = view.txt_delete_edit_offer

        title_tab_offer!!.setText(title)

        txt_delete_edit_offer!!.setOnClickListener(View.OnClickListener {
            Dialogs.showDialogDeleteOffer(parentActivity!!,dataOfferDetail!!,{ dialogSmall: Dialog, itemDto : DataOfferDetail ->
                dialogSmall.dismiss()
            })
        })

        img_offer_type_edit_offer!!.setOnClickListener(View.OnClickListener {
            Dialogs.showDialogSelectTypeOffer(parentActivity!!,{ dialogNew: Dialog, pos : Int ->
                if(pos == 0){
                    txt_select_type_edit_offer!!.setText(parentActivity!!.getString(R.string.fashion))
                }else if(pos == 1){
                    txt_select_type_edit_offer!!.setText(parentActivity!!.getString(R.string.service))
                }
                dialogNew.dismiss()
            })
        })

        listImage.add(ImageEditOfferModel("android.resource://com.example.shop49k/drawable/ic_back_1_sale_detail",null,0))
        listImage.add(ImageEditOfferModel("android.resource://com.example.shop49k/drawable/ic_back_2_sale_detail",null,1))
        var imageEditOfferAdapter : ImageEditOfferAdapter? = null
        imageEditOfferAdapter = ImageEditOfferAdapter(parentActivity!!, { itemDto: ImageEditOfferModel, position: Int ->
            listImage.removeAt(position)
            imageEditOfferAdapter!!.updatePosts(listImage,false)

        })
        ryv_image_edit_offer!!.adapter = imageEditOfferAdapter
        ryv_image_edit_offer!!.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.HORIZONTAL, false)
        imageEditOfferAdapter!!.updatePosts(listImage,false)

        if(dataOfferDetail != null){
            if(dataOfferDetail!!.status==0){
                layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_gray)
                txt_status_edit_offer!!.setTextColor(context.resources.getColor(R.color.text_hide))
                txt_status_edit_offer!!.setText(context.getString(R.string.pending))
            }else if(dataOfferDetail!!.status==1){
                layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_blue)
                txt_status_edit_offer!!.setTextColor(context.resources.getColor(R.color.text_blue))
                txt_status_edit_offer!!.setText(context.getString(R.string.running))
            }else if(dataOfferDetail!!.status==2){
                layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_green)
                txt_status_edit_offer!!.setTextColor(context.resources.getColor(R.color.text_green_home))
                txt_status_edit_offer!!.setText(context.getString(R.string.finish))
            }else if(dataOfferDetail!!.status==3){
                layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_red)
                txt_status_edit_offer!!.setTextColor(context.resources.getColor(R.color.button_login))
                txt_status_edit_offer!!.setText(context.getString(R.string.cancel))
            }

            txt_delete_edit_offer!!.visibility = View.VISIBLE
        }else{
            layout_status_edit_offer!!.setBackgroundResource(R.drawable.custom_status_offer_item_gray)
            txt_status_edit_offer!!.setTextColor(context.resources.getColor(R.color.text_hide))
            txt_status_edit_offer!!.setText(context.getString(R.string.drap))

            txt_delete_edit_offer!!.visibility = View.GONE
        }

        txt_save_edit_offer!!.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        img_back_edit_offer!!.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

//        dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
//            if(keyCode == KeyEvent.KEYCODE_BACK){
//                dialog.dismiss()
//            }
//            true
//        })
        return view
    }
    companion object {
        fun newInstance(parentActivity: MainActivity, DataOfferDetail: DataOfferDetail, title:String, typeDialog:Int): EditOfferDialog {
            val f = EditOfferDialog()

            // Supply num input as an argument.
            val args = Bundle()
            args.putSerializable("parentActivity",parentActivity)
            args.putParcelable("DataOfferDetail",DataOfferDetail)
            args.putString("title",title)
            // 0 : create , 1 : edit
            args.putInt("typeDialog",typeDialog)
            f.arguments = args

            return f
        }
    }


}