package com.example.shop49k.ui.fragment.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import app.telesense.android.utils.ClickListener
import app.telesense.android.utils.PaginationScrollListener
import app.telesense.android.utils.RecyclerTouchListener
import com.example.shop49k.R
import com.example.shop49k.base.BaseFragment
import com.example.shop49k.model.chart.ChartDetail
import com.example.shop49k.model.home.HomeDetail
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.ui.smallActivity.qrCode.QrCodeActivity
import com.example.shop49k.utils.`object`.CommonUtil
import com.github.mikephil.charting.charts.LineChart
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment<HomePresenter>(), HomeView , MainActivity.OnBackPressedListner{


    override fun onBackPressedFragment(): Boolean {
//        parentActivity!!.addFragment(4)
        return false
    }

    override fun instantiatePresenter(): HomePresenter {
        return HomePresenter(this)    }

    override fun getContext(): Context {
        return parentActivity!!.getContext()
    }

    private var ryv_recent_orders: RecyclerView? = null
    private var homeAdapter: HomeAdapter? = null
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var listHomeDetail : ArrayList<HomeDetail> = ArrayList()
    var layout_view_all:LinearLayout? = null
    private var chart_home : LineChart? = null
    var listDataChart : ArrayList<ChartDetail> = ArrayList()
    var layout_scroll:ScrollView? = null
    var txt_view_all_recent_orders : TextView? = null
    var layout_manage_offers : LinearLayout? = null
    var layout_check_qr_code : LinearLayout? = null
    var txt_num_offer : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        ryv_recent_orders = view.ryv_recent_orders
        layout_view_all = view.layout_view_all
        chart_home = view.chart_home
        layout_scroll = view.layout_scroll
        txt_view_all_recent_orders = view.txt_view_all_recent_orders
        layout_manage_offers = view.layout_manage_offers
        layout_check_qr_code = view.layout_check_qr_code
        txt_num_offer = view.txt_num_offer

        listDataChart.add(ChartDetail(10.0))
        listDataChart.add(ChartDetail(19.0))
        listDataChart.add(ChartDetail(6.0))
        listDataChart.add(ChartDetail(29.0))
        listDataChart.add(ChartDetail(13.0))
        listDataChart.add(ChartDetail(17.0))
        listDataChart.add(ChartDetail(10.0))
        listDataChart.add(ChartDetail(18.0))

        if(parentActivity!!.dataUserInfo != null){
            txt_num_offer!!.setText(parentActivity!!.dataUserInfo!!.data.offer_count.toString())
        }else{
            txt_num_offer!!.setText("")
        }

        CommonUtil.setupLineChartDataAlert(chart_home!!,listDataChart,parentActivity!!)

        homeAdapter = HomeAdapter(parentActivity!!,{ itemDto: HomeDetail, position: Int ->

        },{ itemDto: HomeDetail, position: Int ->
            listHomeDetail.removeAt(position)
            homeAdapter!!.updatePosts(listHomeDetail,false)
        })

        ryv_recent_orders!!.adapter = homeAdapter
        ryv_recent_orders!!.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)

        ryv_recent_orders!!.addOnItemTouchListener(RecyclerTouchListener(parentActivity!!.applicationContext, ryv_recent_orders!!, object :
            ClickListener {


            override fun onClick(view: View, position: Int) {

//                Toast.makeText(this@MainActivity, imageModelArrayList!![position].getNames(), Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))

        ryv_recent_orders!!.addOnScrollListener(object : PaginationScrollListener((ryv_recent_orders!!.layoutManager as LinearLayoutManager?)!!) {
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

        listHomeDetail.add(HomeDetail(R.drawable.ic_back_item_1, "Get up to 80% off. Buy headphones …",42,"#432136"))
        listHomeDetail.add(HomeDetail(R.drawable.ic_back_item_2, "Get up to 80% off. Buy headphones …",21,"#432136"))
        listHomeDetail.add(HomeDetail(R.drawable.ic_back_item_1, "Get up to 80% off. Buy headphones …",44,"#432136"))
        listHomeDetail.add(HomeDetail(R.drawable.ic_back_item_2, "Get up to 80% off. Buy headphones …",12,"#432136"))
        listHomeDetail.add(HomeDetail(R.drawable.ic_back_item_1, "Get up to 80% off. Buy headphones …",66,"#432136"))
        listHomeDetail.add(HomeDetail(R.drawable.ic_back_item_2, "Get up to 80% off. Buy headphones …",88,"#432136"))
        listHomeDetail.add(HomeDetail(R.drawable.ic_back_item_1, "Get up to 80% off. Buy headphones …",42,"#432136"))
        listHomeDetail.add(HomeDetail(R.drawable.ic_back_item_2, "Get up to 80% off. Buy headphones …",21,"#432136"))
        listHomeDetail.add(HomeDetail(R.drawable.ic_back_item_1, "Get up to 80% off. Buy headphones …",44,"#432136"))
        listHomeDetail.add(HomeDetail(R.drawable.ic_back_item_2, "Get up to 80% off. Buy headphones …",12,"#432136"))

        homeAdapter!!.updatePosts(listHomeDetail,false)

        layout_view_all!!.setOnClickListener(View.OnClickListener {

        })

        txt_view_all_recent_orders!!.setOnClickListener(View.OnClickListener {
            parentActivity!!.addFragment(4)
        })

        layout_manage_offers!!.setOnClickListener(View.OnClickListener {
//            parentActivity!!.addFragment(1)
            parentActivity!!.button_offer.callOnClick()
        })
        layout_check_qr_code!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(parentActivity!!.applicationContext, QrCodeActivity::class.java)
            startActivity(intent)
        })
        layout_scroll!!.smoothScrollTo(0,0)

        return view
    }

}