package com.example.shop49k.ui.fragment.order

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
import android.widget.RelativeLayout
import app.telesense.android.utils.ClickListener
import app.telesense.android.utils.PaginationScrollListener
import app.telesense.android.utils.RecyclerTouchListener
import com.example.shop49k.R
import com.example.shop49k.base.BaseFragment
import com.example.shop49k.model.order.OrderDetail
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.ui.smallActivity.editOffer.EditOfferActivity
import com.example.shop49k.utils.OFFER_DETAIL
import com.example.shop49k.utils.TITLE
import com.example.shop49k.utils.TYPE_DIALOG
import com.example.shop49k.utils.`object`.Dialogs
import com.example.shop49k.utils.`object`.Order
import kotlinx.android.synthetic.main.fragment_order.view.*
import kotlinx.android.synthetic.main.layout_title_offers.view.*
import kotlinx.android.synthetic.main.layout_title_order.view.*

class OrderFragment : BaseFragment<OrderPresenter>(), OrderView, MainActivity.OnBackPressedListner{


    override fun onBackPressedFragment(): Boolean {
        parentActivity!!.addFragment(0)
        return false
    }

    override fun instantiatePresenter(): OrderPresenter {
        return OrderPresenter(this)    }

    override fun getContext(): Context {
        return parentActivity!!.getContext()
    }

    private var ryv_order: RecyclerView? = null
    private var orderAdapter: OrderAdapter? = null
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var listOrderDetailAll : ArrayList<OrderDetail> = ArrayList()
    // 0 : shipping, 1 : shipped, 2 : return
    var type = 0
    var layout_all_order : RelativeLayout? = null
    var layout_return_product_orders : RelativeLayout? = null
    var layout_shipping_orders : RelativeLayout? = null
    var layout_shipped_order : RelativeLayout? = null

    var txt_all_orders : View? = null
    var txt_shipped_order : View? = null
    var txt_shipping_orders : View? = null
    var txt_return_product_orders : View? = null
    var img_sort_manage_order: ImageView? = null
    var idSort = 0
    var listPlace : ArrayList<String> = ArrayList()
    var img_back_order:ImageView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        ryv_order = view.ryv_orders
        layout_all_order = view.layout_all_order
        layout_return_product_orders = view.layout_return_product_orders
        layout_shipping_orders = view.layout_shipping_orders
        layout_shipped_order = view.layout_shipped_order
        txt_all_orders = view.txt_all_orders
        txt_shipped_order = view.txt_shipped_order
        txt_shipping_orders = view.txt_shipping_orders
        txt_return_product_orders = view.txt_return_product_orders
        img_sort_manage_order = view.img_sort_manage_order
        img_back_order = view.img_back_order

        listPlace.add("Số 54A Nguyễn Chí Thanh, Láng Thượng, Đống Đa, Hà Nội")
        listPlace.add("KiOT 08, Chung cư HH3B, Hoàng Liệt, Hoàng Mai, Hà Nội")
        orderAdapter = OrderAdapter(parentActivity!!,{ itemDto: OrderDetail, position: Int ->
            Order.showDialogOrderDetail(parentActivity!!, itemDto,{ dialogOrder : Dialog ->
                dialogOrder.dismiss()
            },{ dialogOrder : Dialog, orderDetail: OrderDetail ->
            Order.showDialogShowShipId(parentActivity!!,orderDetail,{ dialogShipId : Dialog ->
                dialogShipId.dismiss()
            })
            })
        })

        ryv_order!!.adapter = orderAdapter
        ryv_order!!.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)

        ryv_order!!.addOnItemTouchListener(RecyclerTouchListener(parentActivity!!.applicationContext, ryv_order!!, object :
            ClickListener {


            override fun onClick(view: View, position: Int) {

//                Toast.makeText(this@MainActivity, imageModelArrayList!![position].getNames(), Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))

        ryv_order!!.addOnScrollListener(object : PaginationScrollListener((ryv_order!!.layoutManager as LinearLayoutManager?)!!) {
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

        listOrderDetailAll.add(OrderDetail("Get up to 80% off. Buy headphones, Speakers & …", "#432136","2m ago",0))
        listOrderDetailAll.add(OrderDetail("Mua 1 tặng 1, áp dụng duy nhất 22/07", "#432136","2m ago",1))
        listOrderDetailAll.add(OrderDetail("Sale offer now, up to 35%", "#432136","2m ago",1))
        listOrderDetailAll.add(OrderDetail("Get the special disscount 50% off", "#432136","2m ago",2))
        listOrderDetailAll.add(OrderDetail("Get the special disscount 20% off", "#432136","2m ago",1))
        listOrderDetailAll.add(OrderDetail("Get up to 80% off. Buy headphones, Speakers & …", "#432136","2m ago",0))
        listOrderDetailAll.add(OrderDetail("Mua 1 tặng 1, áp dụng duy nhất 22/07", "#432136","2m ago",0))
        listOrderDetailAll.add(OrderDetail("Get the special disscount 20% off", "#432136","2m ago",2))
        listOrderDetailAll.add(OrderDetail("Get up to 80% off. Buy headphones, Speakers & …", "#432136","2m ago",0))
        listOrderDetailAll.add(OrderDetail("Mua 1 tặng 1, áp dụng duy nhất 22/07", "#432136","2m ago",1))
        listOrderDetailAll.add(OrderDetail("Get up to 80% off. Buy headphones, Speakers & …", "#432136","2m ago",1))
        listOrderDetailAll.add(OrderDetail("Sale offer now, up to 35%", "#432136","2m ago",2))
        listOrderDetailAll.add(OrderDetail("Get up to 80% off. Buy headphones, Speakers & …", "#432136","2m ago",2))
        layout_all_order!!.setOnClickListener(View.OnClickListener {
            txt_all_orders!!.setBackgroundResource(R.color.num_circle_text_units)
            txt_shipping_orders!!.setBackgroundResource(R.color.white)
            txt_shipped_order!!.setBackgroundResource(R.color.white)
            txt_return_product_orders!!.setBackgroundResource(R.color.white)
            orderAdapter!!.updatePosts(listOrderDetailAll,false)
        })
        layout_shipping_orders!!.setOnClickListener(View.OnClickListener {
            txt_all_orders!!.setBackgroundResource(R.color.white)
            txt_shipping_orders!!.setBackgroundResource(R.color.num_circle_text_units)
            txt_shipped_order!!.setBackgroundResource(R.color.white)
            txt_return_product_orders!!.setBackgroundResource(R.color.white)
            type = 0
            orderAdapter!!.updatePosts(getDataType(type),false)
        })
        layout_shipped_order!!.setOnClickListener(View.OnClickListener {
            txt_all_orders!!.setBackgroundResource(R.color.white)
            txt_shipping_orders!!.setBackgroundResource(R.color.white)
            txt_shipped_order!!.setBackgroundResource(R.color.num_circle_text_units)
            txt_return_product_orders!!.setBackgroundResource(R.color.white)
            type = 1
            orderAdapter!!.updatePosts(getDataType(type),false)
        })
        layout_return_product_orders!!.setOnClickListener(View.OnClickListener {
            txt_all_orders!!.setBackgroundResource(R.color.white)
            txt_shipping_orders!!.setBackgroundResource(R.color.white)
            txt_shipped_order!!.setBackgroundResource(R.color.white)
            txt_return_product_orders!!.setBackgroundResource(R.color.num_circle_text_units)
            type = 2
            orderAdapter!!.updatePosts(getDataType(type),false)
        })
        layout_all_order!!.callOnClick()

        img_sort_manage_order!!.setOnClickListener(View.OnClickListener {
            Dialogs.showDialogBottomSortOffer(parentActivity!!,idSort, parentActivity!!.getString(R.string.select_order),{ dialogNew: Dialog, idSort : Int ->
                this.idSort = idSort
                dialogNew.dismiss()
            })
        })
        img_back_order!!.setOnClickListener(View.OnClickListener {
            parentActivity!!.addFragment(0)
        })

        return view
    }

    fun getDataType(type:Int):ArrayList<OrderDetail>{
        var list = ArrayList<OrderDetail>()
        for(item in listOrderDetailAll){
            if(item.status == type){
                list.add(item)
            }
        }
        return list
    }
}