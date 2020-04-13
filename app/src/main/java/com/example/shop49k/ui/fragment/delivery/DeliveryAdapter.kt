package com.example.shop49k.ui.fragment.delivery

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shop49k.R
import com.example.shop49k.model.delivery.DeliveryDetail
import kotlinx.android.synthetic.main.item_delivery.view.*

class DeliveryAdapter (val context: Activity, val clickItemListener: (DeliveryDetail, Int) -> Unit): RecyclerView.Adapter<DeliveryAdapter.ViewHolder>(){
    private var items: List<DeliveryDetail> = listOf()

    override fun getItemCount(): Int {
        return items.size

    }

    fun updatePosts(items: List<DeliveryDetail>, isLoadMore:Boolean) {
        if(isLoadMore){
            this.items+=items
        }else{
            this.items = items
        }
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_delivery, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: DeliveryDetail = items[position]

        if(item.status==0){
            holder?.layout_status_delivery.setBackgroundResource(R.drawable.custom_status_offer_item_gray)
            holder?.txt_status_delivery.setTextColor(context.resources.getColor(R.color.orange))
            holder?.txt_status_delivery.setText(context.getString(R.string.shipping))
        }else if(item.status==1){
            holder?.layout_status_delivery.setBackgroundResource(R.drawable.custom_status_offer_item_green)
            holder?.txt_status_delivery.setTextColor(context.resources.getColor(R.color.text_green_home))
            holder?.txt_status_delivery.setText(context.getString(R.string.shipped))
        }else if(item.status==2){
            holder?.layout_status_delivery.setBackgroundResource(R.drawable.custom_status_offer_item_red)
            holder?.txt_status_delivery.setTextColor(context.resources.getColor(R.color.text_red_home))
            holder?.txt_status_delivery.setText(context.getString(R.string.canceled))
        }
        holder?.txt_title_delivery.setText(item.title)
        holder?.txt_id_delivery.setText(item.id.toString())
        holder?.txt_time_delivery.setText(item.time)

        holder?.layout_item_delivery.setOnClickListener(View.OnClickListener {
            clickItemListener(item,position)
        })
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val txt_title_delivery = view.txt_title_delivery!!
        val txt_id_delivery = view.txt_id_delivery!!
        val txt_time_delivery = view.txt_time_delivery!!
        val layout_status_delivery = view.layout_status_delivery
        val txt_status_delivery = view.txt_status_delivery
        val layout_item_delivery = view.layout_item_delivery
    }
}