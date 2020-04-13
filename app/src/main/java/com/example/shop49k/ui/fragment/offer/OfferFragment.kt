package com.example.shop49k.ui.fragment.offer

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import app.telesense.android.utils.ClickListener
import app.telesense.android.utils.PaginationScrollListener
import app.telesense.android.utils.RecyclerTouchListener
import com.example.shop49k.R
import com.example.shop49k.base.BaseFragment
import com.example.shop49k.model.offer.DataOfferDetail
import com.example.shop49k.ui.smallActivity.editOffer.EditOfferActivity
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.utils.*
import com.example.shop49k.utils.`object`.Dialogs
import kotlinx.android.synthetic.main.fragment_offer.view.*
import kotlinx.android.synthetic.main.layout_title_offers.view.*

class OfferFragment : BaseFragment<OfferPresenter>(), OfferView, MainActivity.OnBackPressedListner{
    override fun getOffersWithIdDataSuccess(dataOfferDetail: DataOfferDetail) {
        prg_offer!!.visibility = View.GONE
        Dialogs.showDialogDataOfferDetail(parentActivity!!,dataOfferDetail,listPlace,{ dialogNew: Dialog ->
            dialogNew.dismiss()

        },{ dialogNew: Dialog, itemDto : DataOfferDetail ->
            val intent = Intent(parentActivity!!.applicationContext, EditOfferActivity::class.java)
            intent.putExtra(OFFER_DETAIL,dataOfferDetail)
            intent.putExtra(TITLE,parentActivity!!.getString(R.string.edit_offer))
            intent.putExtra(TYPE_DIALOG,1)
            startActivity(intent)
            dialogNew.dismiss()
        },{ dialogNew: Dialog, itemDto : DataOfferDetail ->
            Dialogs.showDialogDeleteOffer(parentActivity!!,itemDto,{ dialogSmall: Dialog, itemDto : DataOfferDetail ->
                dialogSmall.dismiss()
            })
        })
    }

    override fun getOffersDataSuccess(dataOfferDetail: List<DataOfferDetail>, isLoadMore : Boolean) {
        if(dataOfferDetail.size < limit){
            isLoading = true
        }
        if(isLoadMore){
            prg_offer!!.visibility = View.GONE
            listDataOfferDetailAll.addAll(dataOfferDetail)
            offerAdapter!!.updatePosts(listDataOfferDetailAll,false)
        }else{
            listDataOfferDetailAll.clear()
            prg_offer!!.visibility = View.GONE
            listDataOfferDetailAll.addAll(dataOfferDetail)
            offerAdapter!!.updatePosts(listDataOfferDetailAll,false)
        }
    }

    override fun errorGetOffers(dataOfferDetail: String) {
        prg_offer!!.visibility = View.GONE
    }


    override fun onBackPressedFragment(): Boolean {
        parentActivity!!.addFragment(0)
        return false
    }

    override fun instantiatePresenter(): OfferPresenter {
        return OfferPresenter(this)    }

    override fun getContext(): Context {
        return parentActivity!!.getContext()
    }

    private var ryv_offers: RecyclerView? = null
    private var offerAdapter: OfferAdapter? = null
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var listDataOfferDetailAll : ArrayList<DataOfferDetail> = ArrayList()
    var listDataOfferDetail : ArrayList<DataOfferDetail> = ArrayList()
    // 0 : pending, 1 : running, 2 : finish
    var status = ""
    var layout_all_offer : RelativeLayout? = null
    var layout_pending_offers : RelativeLayout? = null
    var layout_running_offer : RelativeLayout? = null
    var layout_done_offers : RelativeLayout? = null
    var sort_field = ""
    var sort_order = "asc"
    var page = 1;
    var limit = 10;

    var txt_all_offers : View? = null
    var txt_pending_offers : View? = null
    var txt_running_offer : View? = null
    var txt_done_offers : View? = null
    var img_sort_manage_offer:ImageView? = null
    var prg_offer : ProgressBar? = null
    var idSort = 0
    var listPlace : ArrayList<String> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_offer, container, false)

        ryv_offers = view.ryv_offers
        layout_all_offer = view.layout_all_offer
        layout_pending_offers = view.layout_pending_offers
        layout_running_offer = view.layout_running_offer
        layout_done_offers = view.layout_done_offers
        txt_all_offers = view.txt_all_offers
        txt_pending_offers = view.txt_pending_offers
        txt_running_offer = view.txt_running_offer
        txt_done_offers = view.txt_done_offers
        img_sort_manage_offer = view.img_sort_manage_offer
        prg_offer = view.prg_offer

        presenter!!.getOffers(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""),sort_field,sort_order,status.toString(),
            limit.toString(),page.toString(),false)
        prg_offer!!.visibility = View.VISIBLE

        offerAdapter = OfferAdapter(parentActivity!!,{ itemDto: DataOfferDetail, position: Int ->
            prg_offer!!.visibility = View.VISIBLE
            presenter!!.getOffersWithId(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""),sort_field,sort_order, itemDto.id.toString())

        })

        ryv_offers!!.adapter = offerAdapter
        ryv_offers!!.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)

        ryv_offers!!.addOnItemTouchListener(RecyclerTouchListener(parentActivity!!.applicationContext, ryv_offers!!, object :
            ClickListener {


            override fun onClick(view: View, position: Int) {

//                Toast.makeText(this@MainActivity, imageModelArrayList!![position].getNames(), Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))

        ryv_offers!!.addOnScrollListener(object : PaginationScrollListener((ryv_offers!!.layoutManager as LinearLayoutManager?)!!) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                page+=1
                prg_offer!!.visibility = View.VISIBLE
                presenter!!.getOffers(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""),sort_field,sort_order,status.toString(),
                    limit.toString(),page.toString(),true)
//                getMoreItems()
            }
        })

        layout_all_offer!!.setOnClickListener(View.OnClickListener {
            txt_all_offers!!.setBackgroundResource(R.color.num_circle_text_units)
            txt_pending_offers!!.setBackgroundResource(R.color.white)
            txt_running_offer!!.setBackgroundResource(R.color.white)
            txt_done_offers!!.setBackgroundResource(R.color.white)
            status = ""
            page = 1
            prg_offer!!.visibility = View.VISIBLE
            isLoading = false
            presenter!!.getOffers(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""),sort_field,sort_order,status.toString(),
                limit.toString(),page.toString(),false)
        })
        layout_pending_offers!!.setOnClickListener(View.OnClickListener {
            txt_all_offers!!.setBackgroundResource(R.color.white)
            txt_pending_offers!!.setBackgroundResource(R.color.num_circle_text_units)
            txt_running_offer!!.setBackgroundResource(R.color.white)
            txt_done_offers!!.setBackgroundResource(R.color.white)
            status = "0"
            page = 1
            prg_offer!!.visibility = View.VISIBLE
            isLoading = false
            presenter!!.getOffers(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""),sort_field,sort_order,status.toString(),
                limit.toString(),page.toString(),false)
        })
        layout_running_offer!!.setOnClickListener(View.OnClickListener {
            txt_all_offers!!.setBackgroundResource(R.color.white)
            txt_pending_offers!!.setBackgroundResource(R.color.white)
            txt_running_offer!!.setBackgroundResource(R.color.num_circle_text_units)
            txt_done_offers!!.setBackgroundResource(R.color.white)
            status = "1"
            page = 1
            prg_offer!!.visibility = View.VISIBLE
            isLoading = false
            presenter!!.getOffers(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""),sort_field,sort_order,status.toString(),
                limit.toString(),page.toString(),false)
        })
        layout_done_offers!!.setOnClickListener(View.OnClickListener {
            txt_all_offers!!.setBackgroundResource(R.color.white)
            txt_pending_offers!!.setBackgroundResource(R.color.white)
            txt_running_offer!!.setBackgroundResource(R.color.white)
            txt_done_offers!!.setBackgroundResource(R.color.num_circle_text_units)
            status = "2"
            page = 1
            prg_offer!!.visibility = View.VISIBLE
            isLoading = false
            presenter!!.getOffers(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""),sort_field,sort_order,status.toString(),
                limit.toString(),page.toString(),false)
        })
        layout_all_offer!!.callOnClick()

        img_sort_manage_offer!!.setOnClickListener(View.OnClickListener {
            Dialogs.showDialogBottomSortOffer(parentActivity!!,idSort, parentActivity!!.getString(R.string.select_offer),{ dialogNew: Dialog, idSort : Int ->
                this.idSort = idSort
                dialogNew.dismiss()
                page = 1
                prg_offer!!.visibility = View.VISIBLE
                isLoading = false
                if(idSort == 0){
                    sort_field = ""
                }else if(idSort == 1){
                    sort_field = "updated_time"
                }else if(idSort == 2){
                    sort_field = "title"
                }
                presenter!!.getOffers(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""),sort_field,sort_order,status.toString(),
                    limit.toString(),page.toString(),false)
            })
        })
        return view
    }

    fun getDataType(type:Int):ArrayList<DataOfferDetail>{
        var list = ArrayList<DataOfferDetail>()
        for(item in listDataOfferDetailAll){
            if(item.status == type){
                list.add(item)
            }
        }
        return list
    }
}