package com.example.shop49k.ui.fragment.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shop49k.R
import com.example.shop49k.model.home.HomeDetail
import com.example.shop49k.ui.main.MainActivity
import kotlinx.android.synthetic.main.item_recent_order.view.*

class HomeAdapter (val context: MainActivity, val clickSuccessListener: (HomeDetail, Int) -> Unit,
                   val clickDeleteListener: (HomeDetail, Int) -> Unit): RecyclerView.Adapter<HomeAdapter.ViewHolder>(){
    private var items: List<HomeDetail> = listOf()

    override fun getItemCount(): Int {
        return items.size

    }

    fun updatePosts(items: List<HomeDetail>,isLoadMore:Boolean) {
        if(isLoadMore){
            this.items+=items
        }else{
            this.items = items
        }
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recent_order, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: HomeDetail = items[position]

        holder?.txt_name_order.setText(item.name)
        holder?.txt_number_order.setText("("+item.total.toString()+")")
        holder?.txt_id_order.setText(item.id.toString())
        holder?.img_left.setImageResource(item.image)

        holder?.img_success_item.setOnClickListener(View.OnClickListener {
            clickSuccessListener(item,position)
        })
        holder?.img_delete_item.setOnClickListener(View.OnClickListener {
            clickDeleteListener(item,position)
        })
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val txt_name_order = view.txt_name_order!!
        val txt_number_order = view.txt_number_order!!
        val txt_id_order = view.txt_id_order!!
        val img_delete_item = view.img_delete_item!!
        val img_success_item = view.img_success_item!!
        val img_left = view.img_left!!
    }
}