package com.example.shop49k.ui.fragment.order

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.shop49k.R
import com.example.shop49k.model.order.OrderDetail
import com.example.shop49k.ui.main.MainActivity
import kotlinx.android.synthetic.main.item_order.view.*

class OrderAdapter (val context: MainActivity, val clickItemListener: (OrderDetail, Int) -> Unit): RecyclerView.Adapter<OrderAdapter.ViewHolder>(){
    private var items: List<OrderDetail> = listOf()

    override fun getItemCount(): Int {
        return items.size

    }

    fun updatePosts(items: List<OrderDetail>, isLoadMore:Boolean) {
        if(isLoadMore){
            this.items+=items
        }else{
            this.items = items
        }
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: OrderDetail = items[position]

        if(item.status==0){
            holder?.layout_status_order.setBackgroundResource(R.drawable.custom_status_offer_item_gray)
            holder?.txt_status_order.setTextColor(context.resources.getColor(R.color.orange))
            holder?.txt_status_order.setText(context.getString(R.string.shipping))
        }else if(item.status==1){
            holder?.layout_status_order.setBackgroundResource(R.drawable.custom_status_offer_item_green)
            holder?.txt_status_order.setTextColor(context.resources.getColor(R.color.text_green_home))
            holder?.txt_status_order.setText(context.getString(R.string.shipped))
        }else if(item.status==2){
            holder?.layout_status_order.setBackgroundResource(R.drawable.custom_status_offer_item_red)
            holder?.txt_status_order.setTextColor(context.resources.getColor(R.color.text_red_home))
            holder?.txt_status_order.setText(context.getString(R.string.canceled))
        }
        holder?.txt_title_order.setText(item.title)
        holder?.txt_id_order.setText(item.id.toString())
        holder?.txt_time_order.setText(item.time)
        holder?.img_left_order!!.scaleType = ImageView.ScaleType.CENTER_CROP

        holder?.layout_item_order.setOnClickListener(View.OnClickListener {
            clickItemListener(item,position)
        })
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val txt_title_order = view.txt_title_order!!
        val txt_id_order = view.txt_id_order!!
        val txt_time_order = view.txt_time_order!!
        val layout_status_order = view.layout_status_order
        val txt_status_order = view.txt_status_order
        val layout_item_order = view.layout_item_order
        val img_left_order = view.img_left_order
        val prg_order_image = view.prg_order_image
    }
}