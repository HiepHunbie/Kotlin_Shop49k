package com.example.shop49k.ui.smallActivity.personInfo

import android.app.Activity
import android.os.Handler
import android.support.v4.os.HandlerCompat.postDelayed
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shop49k.R
import com.example.shop49k.model.bottomPopup.BottomPopupModel
import com.example.shop49k.ui.main.MainActivity
import kotlinx.android.synthetic.main.item_popup_select_bottom.view.*

class PopupBottomAdapter (val context: Activity, val clickItemListener: (BottomPopupModel, Int) -> Unit): RecyclerView.Adapter<PopupBottomAdapter.ViewHolder>(){
    private var items: List<BottomPopupModel> = listOf()

    override fun getItemCount(): Int {
        return items.size

    }

    fun updatePosts(items: List<BottomPopupModel>, isLoadMore:Boolean) {
        if(isLoadMore){
            this.items+=items
        }else{
            this.items = items
        }
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_popup_select_bottom, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: BottomPopupModel = items[position]

        holder?.txt_value.setText(item.value)

        holder?.layout_item_popup.setOnClickListener(View.OnClickListener {
            holder?.layout_item_popup.setBackgroundResource(R.color.background_main)
            val updateHandler = Handler()

            val runnable = Runnable {
                clickItemListener(item,position)
            }

            updateHandler.postDelayed(runnable, 300)

        })
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val txt_value = view.txt_value
        val layout_item_popup = view.layout_item_popup
    }
}