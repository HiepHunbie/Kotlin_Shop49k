package com.example.shop49k.ui.dialog.offer

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.shop49k.R
import com.example.shop49k.base.BaseDialogFragment
import com.example.shop49k.model.city.DataCityDetail
import com.example.shop49k.model.offer.AddNewData
import com.example.shop49k.model.offer.AddressDetail
import com.example.shop49k.model.offer.DataOfferDetail
import com.example.shop49k.model.offer.addAddress.AddAddressInput
import com.example.shop49k.model.offer.addAddress.Address
import com.example.shop49k.model.offer.addAddress.Offer_address
import com.example.shop49k.model.offer.editOffer.OfferResult
import com.example.shop49k.ui.smallActivity.editOffer.EditOfferActivity
import com.example.shop49k.ui.smallActivity.editOffer.EditOfferPresenter
import com.example.shop49k.ui.smallActivity.editOffer.EditOfferView
import com.example.shop49k.utils.TOKEN
import com.example.shop49k.utils.`object`.Dialogs
import fr.ganfra.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.dialog_add_new_place.view.*

class DialogAddNewPlace : BaseDialogFragment<EditOfferPresenter>(),EditOfferView{
    override fun instantiatePresenter(): EditOfferPresenter {
        return EditOfferPresenter(this)
    }

    override fun getContext(): Context {
        return activity!!.getContext()
    }

    var activity : EditOfferActivity? = null
    var offerDetail : DataOfferDetail? = null
    var proviceSelect : AddNewData? = null
    var districtSelect : AddNewData? = null
    var wardSelect : AddNewData? = null
    var listSelectProvice : ArrayList<AddNewData> = ArrayList()
    var listSelectDistrict : ArrayList<AddNewData> = ArrayList()
    var listSelectWard : ArrayList<AddNewData> = ArrayList()
    var spn_add_place_provice_and_city : MaterialSpinner? = null
    var spn_add_place_district : MaterialSpinner? = null
    var spn_add_place_ward : MaterialSpinner? = null
    var listProvince : ArrayList<String> = ArrayList()
    var listDistrict : ArrayList<String> = ArrayList()
    var listWard : ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.activity = arguments!!.getSerializable("activity") as EditOfferActivity?
        this.offerDetail = arguments!!.getParcelable<DataOfferDetail>("offerDetail")
        // Pick a style based on the num.
//        val style = DialogFragment.STYLE_NO_FRAME
//        val theme = R.style.DialogCustom
//        setStyle(style, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.dialog_add_new_place, container, false)

        presenter!!.getCity(activity!!,"query","")

        spn_add_place_provice_and_city = rootView.spn_add_place_provice_and_city
        spn_add_place_district = rootView.spn_add_place_district
        spn_add_place_ward = rootView.spn_add_place_ward

        rootView.spn_add_place_provice_and_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(position>-1){
                    proviceSelect = listSelectProvice[position]
                    presenter!!.getDistrict(activity!!,"query","",proviceSelect!!.id.toString())}
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        rootView.spn_add_place_district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(position>-1){
                    districtSelect = listSelectDistrict[position]
                    presenter!!.getCommune(activity!!,"query","",districtSelect!!.id.toString())}
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        rootView.spn_add_place_ward.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if(position>-1){
                    wardSelect = listSelectWard[position]}
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        rootView.txt_done_add_new_place.setOnClickListener {
            var listOfferAddress : ArrayList<Offer_address> = ArrayList()
            listOfferAddress.add((Offer_address("add", listOf(
                Address(proviceSelect!!.id,proviceSelect!!.value,districtSelect!!.id,districtSelect!!.value,
                    wardSelect!!.id,wardSelect!!.value,rootView.edt_your_number_home_and_street_add_new_place.text.toString(),offerDetail!!.id)
            ))))
            listOfferAddress.add((Offer_address("delete", listOf())))
            listOfferAddress.add((Offer_address("update", listOf())))
            var address = AddAddressInput(offerDetail!!.id,listOfferAddress)
            presenter!!.addNewAddress(activity!!.prefs!!.getString(TOKEN, ""),address,rootView.prg_add_new_place,activity!!,dialog)
            activity!!.listPlace.add(
                AddressDetail("",proviceSelect!!.id.toString(),proviceSelect!!.value,districtSelect!!.id.toString(),districtSelect!!.value,
                    wardSelect!!.id.toString(),wardSelect!!.value,"",rootView.edt_your_number_home_and_street_add_new_place.text.toString(),"",offerDetail!!.id.toString())
            )
            activity!!.placeShopAdapter!!.updatePosts(activity!!.listPlace,false)
        }
        rootView.txt_cancel_add_new_place.setOnClickListener {
//            clickCanceltListener(mDialog)
            dialog.dismiss()
        }
        dialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK){
                dialog.dismiss()
            }
            false
        })
        return rootView
    }
    override fun successEditOffer(offerResult: OfferResult, dialog: Dialog?) {
    }

    override fun errorEditOffer(offerResult: String) {
        Dialogs.showToastErrorNewNotListenerClick(activity!!,offerResult)
    }

    override fun successAddNewAddress(offerResult: OfferResult, dialog: Dialog?) {
        dialog!!.dismiss()
    }

    override fun errorAddNewAddress(offerResult: String) {
        Dialogs.showToastErrorNewNotListenerClick(activity!!,offerResult)
    }

    override fun getCitySuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectProvice.clear()
        listProvince.clear()
        for(item in dataCityDetail){
            listSelectProvice.add(AddNewData(item.id,item.text))
        }
        for(item in listSelectProvice){
            listProvince.add(item.value)
        }
        val arrayAdapterProvince = ArrayAdapter(activity, R.layout.item_spinner, listProvince )
        spn_add_place_provice_and_city!!.adapter = arrayAdapterProvince
    }

    override fun getDistrictSuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectDistrict.clear()
        listDistrict.clear()
        for(item in dataCityDetail){
            listSelectDistrict.add(AddNewData(item.id,item.text))
        }
        for(item in listSelectDistrict){
            listDistrict.add(item.value)
        }
        val arrayAdapterDistrict = ArrayAdapter(activity, R.layout.item_spinner, listDistrict )
        spn_add_place_district!!.adapter = arrayAdapterDistrict
    }

    override fun getCommueSuccess(dataCityDetail: List<DataCityDetail>) {
        listSelectWard.clear()
        listWard.clear()
        for(item in dataCityDetail){
            listSelectWard.add(AddNewData(item.id,item.text))
        }
        for(item in listSelectWard){
            listWard.add(item.value)
        }
        val arrayAdapterWard = ArrayAdapter(activity, R.layout.item_spinner, listWard )
        spn_add_place_ward!!.adapter = arrayAdapterWard
    }

    companion object {
        fun newInstance(activity: EditOfferActivity, offerDetail: DataOfferDetail): DialogAddNewPlace {
            val f = DialogAddNewPlace()

            // Supply num input as an argument.
            val args = Bundle()
            args.putSerializable("activity",activity)
            args.putParcelable("offerDetail", offerDetail)
            f.arguments = args

            return f
        }
    }
}