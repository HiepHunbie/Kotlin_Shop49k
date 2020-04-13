package com.example.shop49k.ui.fragment.sale

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import app.telesense.android.utils.ClickListener
import app.telesense.android.utils.PaginationScrollListener
import app.telesense.android.utils.RecyclerTouchListener
import com.example.shop49k.R
import com.example.shop49k.base.BaseFragment
import com.example.shop49k.model.sale.DataSaleDetail
import com.example.shop49k.model.sale.SaleDetail
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.utils.`object`.Dialogs
import kotlinx.android.synthetic.main.fragment_sale.view.*

class SaleFragment : BaseFragment<SalePresenter>(), SaleView, MainActivity.OnBackPressedListner{
    override fun getListSaleSuccess(dataSaleDetail: List<DataSaleDetail>) {
        listSaleDetail.addAll(dataSaleDetail)
        saleAdapter!!.updatePosts(listSaleDetail,false)
        prg_sale!!.visibility = View.GONE
    }

    override fun getListSaleError(mess: String) {
        prg_sale!!.visibility = View.GONE
    }


    override fun onBackPressedFragment(): Boolean {
        parentActivity!!.addFragment(0)
        return false
    }

    override fun instantiatePresenter(): SalePresenter {
        return SalePresenter(this)    }

    override fun getContext(): Context {
        return parentActivity!!.getContext()
    }

    private var ryv_sales: RecyclerView? = null
    private var saleAdapter: SaleAdapter? = null
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var listSaleDetail : ArrayList<DataSaleDetail> = ArrayList()
    private var prg_sale : ProgressBar? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sale, container, false)

        ryv_sales = view.ryv_sales
        prg_sale = view.prg_sale

        presenter!!.getListSale(parentActivity!!)
        prg_sale!!.visibility = View.VISIBLE
        saleAdapter = SaleAdapter(parentActivity!!,{ itemDto: DataSaleDetail, position: Int ->
//            listSaleDetail[position].is_read = true
            saleAdapter!!.updatePosts(listSaleDetail,false)
            Dialogs.showDialogSaleDetail(parentActivity!!,itemDto,{ dialogNew: Dialog ->
                dialogNew.dismiss()
            })
        })

        ryv_sales!!.adapter = saleAdapter
        ryv_sales!!.layoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)

        ryv_sales!!.addOnItemTouchListener(RecyclerTouchListener(parentActivity!!.applicationContext, ryv_sales!!, object :
            ClickListener {


            override fun onClick(view: View, position: Int) {

//                Toast.makeText(this@MainActivity, imageModelArrayList!![position].getNames(), Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))

        ryv_sales!!.addOnScrollListener(object : PaginationScrollListener((ryv_sales!!.layoutManager as LinearLayoutManager?)!!) {
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

//        listSaleDetail.add(SaleDetail(true, "Mua 1 tặng 1, áp dụng duy nhất 26/09","5m ago"))
//        listSaleDetail.add(SaleDetail(true, "Giảm 20% khi rút tiền qua MSB","10:31 PM"))
//        listSaleDetail.add(SaleDetail(false, "Nhận ngay ưu đãi khủng khi tạo offer vào ngày 23/09/2019.","10:31 PM"))
//        listSaleDetail.add(SaleDetail(false, "Tạo offer đầu tiên, nhận ngay 100k","08:54 PM"))

//        saleAdapter!!.updatePosts(listSaleDetail,false)
        return view
    }

    }