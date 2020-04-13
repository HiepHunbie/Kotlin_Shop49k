package com.example.shop49k.ui.fragment.offer

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shop49k.R
import com.example.shop49k.model.offer.AddressDetail
import com.example.shop49k.ui.main.MainActivity
import kotlinx.android.synthetic.main.item_place_offer_detail.view.*

class PlaceShopAdapter (val context: Activity): RecyclerView.Adapter<PlaceShopAdapter.ViewHolder>(){
    private var items: List<AddressDetail> = listOf()

    override fun getItemCount(): Int {
        return items.size

    }

    fun updatePosts(items: List<AddressDetail>, isLoadMore:Boolean) {
        if(isLoadMore){
            this.items+=items
        }else{
            this.items = items
        }
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_place_offer_detail, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: String = items[position].external_address + ","+ items[position].commune_name + ","+items[position].district_name + ","+
                items[position].city_name

        holder?.txt_place.setText(item)
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val txt_place = view.txt_place!!
    }
}