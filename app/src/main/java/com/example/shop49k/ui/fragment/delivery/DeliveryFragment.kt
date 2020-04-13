package com.example.shop49k.ui.fragment.delivery

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.telesense.android.utils.ClickListener
import app.telesense.android.utils.PaginationScrollListener
import app.telesense.android.utils.RecyclerTouchListener
import com.example.shop49k.R
import com.example.shop49k.base.BaseFragment
import com.example.shop49k.model.delivery.DeliveryDetail
import com.example.shop49k.ui.fragment.order.OrderAdapter
import com.example.shop49k.ui.shipperMain.ShipperMainActivity
import com.example.shop49k.utils.`object`.Order
import kotlinx.android.synthetic.main.fragment_delivery.view.*
import kotlinx.android.synthetic.main.fragment_order.view.*

class DeliveryFragment  : BaseFragment<DeliveryPresenter>(), DeliveryView, ShipperMainActivity.OnBackPressedListner{


    override fun onBackPressedFragment(): Boolean {
        shipperMainActivity!!.addFragment(0)
        return false
    }

    override fun instantiatePresenter(): DeliveryPresenter {
        return DeliveryPresenter(this)    }

    override fun getContext(): Context {
        return shipperMainActivity!!.getContext()
    }

    private var ryv_delivery: RecyclerView? = null
    private var deliveryAdapter: DeliveryAdapter? = null
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var listDeliveryDetailAll : ArrayList<DeliveryDetail> = ArrayList()
    // 0 : shipping, 1 : shipped, 2 : return
    var type = 0
    var listPlace : ArrayList<String> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_delivery, container, false)

        ryv_delivery = view.ryv_deliverys
        
        deliveryAdapter = DeliveryAdapter(shipperMainActivity!!,{ itemDto: DeliveryDetail, position: Int ->
        })

        ryv_delivery!!.adapter = deliveryAdapter
        ryv_delivery!!.layoutManager = LinearLayoutManager(shipperMainActivity, LinearLayoutManager.VERTICAL, false)

        ryv_delivery!!.addOnItemTouchListener(RecyclerTouchListener(shipperMainActivity!!.applicationContext, ryv_delivery!!, object :
            ClickListener {


            override fun onClick(view: View, position: Int) {

//                Toast.makeText(this@MainActivity, imageModelArrayList!![position].getNames(), Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))

        ryv_delivery!!.addOnScrollListener(object : PaginationScrollListener((ryv_delivery!!.layoutManager as LinearLayoutManager?)!!) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
//                getMoreItems()
            }
        })

        listDeliveryDetailAll.add(DeliveryDetail("Get up to 80% off. Buy headphones, Speakers & …", "#432136","2m ago",0))
        listDeliveryDetailAll.add(DeliveryDetail("Mua 1 tặng 1, áp dụng duy nhất 22/07", "#432136","2m ago",1))
        listDeliveryDetailAll.add(DeliveryDetail("Sale offer now, up to 35%", "#432136","2m ago",1))
        listDeliveryDetailAll.add(DeliveryDetail("Get the special disscount 50% off", "#432136","2m ago",2))
        listDeliveryDetailAll.add(DeliveryDetail("Get the special disscount 20% off", "#432136","2m ago",1))
        listDeliveryDetailAll.add(DeliveryDetail("Get up to 80% off. Buy headphones, Speakers & …", "#432136","2m ago",0))
        listDeliveryDetailAll.add(DeliveryDetail("Mua 1 tặng 1, áp dụng duy nhất 22/07", "#432136","2m ago",0))
        listDeliveryDetailAll.add(DeliveryDetail("Get the special disscount 20% off", "#432136","2m ago",2))
        listDeliveryDetailAll.add(DeliveryDetail("Get up to 80% off. Buy headphones, Speakers & …", "#432136","2m ago",0))
        listDeliveryDetailAll.add(DeliveryDetail("Mua 1 tặng 1, áp dụng duy nhất 22/07", "#432136","2m ago",1))
        listDeliveryDetailAll.add(DeliveryDetail("Get up to 80% off. Buy headphones, Speakers & …", "#432136","2m ago",1))
        listDeliveryDetailAll.add(DeliveryDetail("Sale offer now, up to 35%", "#432136","2m ago",2))
        listDeliveryDetailAll.add(DeliveryDetail("Get up to 80% off. Buy headphones, Speakers & …", "#432136","2m ago",2))
        deliveryAdapter!!.updatePosts(listDeliveryDetailAll,false)
        return view
    }
}