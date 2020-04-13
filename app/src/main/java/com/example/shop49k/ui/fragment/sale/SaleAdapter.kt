package com.example.shop49k.ui.fragment.sale

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shop49k.R
import com.example.shop49k.model.sale.DataSaleDetail
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.utils.`object`.DateUtil
import kotlinx.android.synthetic.main.item_sale.view.*

class SaleAdapter (val context: MainActivity, val clickItemListener: (DataSaleDetail, Int) -> Unit): RecyclerView.Adapter<SaleAdapter.ViewHolder>(){
    private var items: List<DataSaleDetail> = listOf()

    override fun getItemCount(): Int {
        return items.size

    }

    fun updatePosts(items: List<DataSaleDetail>,isLoadMore:Boolean) {
        if(isLoadMore){
            this.items+=items
        }else{
            this.items = items
        }
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sale, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: DataSaleDetail = items[position]

        if(item.is_read){
            holder?.img_view_status.visibility = View.VISIBLE
        }else{
            holder?.img_view_status.visibility = View.INVISIBLE
        }
        holder?.txt_title_sale.setText(item.title)
        if(item.created_at != null){
            holder?.txt_time_ago_sale.setText(DateUtil.convertDateToStringHasHour(item.created_at))
            holder?.txt_order_date_first.setText(DateUtil.convertDateToStringFirstSale(item.created_at))
        }

        holder?.layout_item.setOnClickListener(View.OnClickListener {
            clickItemListener(item,position)
        })
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val img_view_status = view.img_view_status!!
        val txt_title_sale = view.txt_title_sale!!
        val txt_time_ago_sale = view.txt_time_ago_sale!!
        val layout_item = view.layout_item
        val txt_order_date_first = view.txt_order_date_first
    }
}