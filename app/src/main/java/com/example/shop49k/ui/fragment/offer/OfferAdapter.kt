package com.example.shop49k.ui.fragment.offer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.shop49k.R
import com.example.shop49k.model.offer.DataOfferDetail
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.utils.`object`.Image
import kotlinx.android.synthetic.main.dialog_view_iamge_detail.view.*
import kotlinx.android.synthetic.main.item_offer.view.*

class OfferAdapter (val context: MainActivity, val clickItemListener: (DataOfferDetail, Int) -> Unit): RecyclerView.Adapter<OfferAdapter.ViewHolder>(){
    private var items: List<DataOfferDetail> = listOf()

    override fun getItemCount(): Int {
        return items.size

    }

    fun updatePosts(items: List<DataOfferDetail>, isLoadMore:Boolean) {
        if(isLoadMore){
            this.items+=items
        }else{
            this.items = items
        }
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_offer, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: DataOfferDetail = items[position]

        if(item.status==0){
            holder?.layout_status_offer.setBackgroundResource(R.drawable.custom_status_offer_item_orange)
            holder?.txt_status_offer.setTextColor(context.resources.getColor(R.color.text_hide))
            holder?.txt_status_offer.setText(context.getString(R.string.pending))
        }else if(item.status==1){
            holder?.layout_status_offer.setBackgroundResource(R.drawable.custom_status_offer_item_blue)
            holder?.txt_status_offer.setTextColor(context.resources.getColor(R.color.text_blue))
            holder?.txt_status_offer.setText(context.getString(R.string.running))
        }else if(item.status==2){
            holder?.layout_status_offer.setBackgroundResource(R.drawable.custom_status_offer_item_green)
            holder?.txt_status_offer.setTextColor(context.resources.getColor(R.color.text_green_home))
            holder?.txt_status_offer.setText(context.getString(R.string.finish))
        }else if(item.status==3){
            holder?.layout_status_offer.setBackgroundResource(R.drawable.custom_status_offer_item_red)
            holder?.txt_status_offer.setTextColor(context.resources.getColor(R.color.button_login))
            holder?.txt_status_offer.setText(context.getString(R.string.cancel))
        }
        holder?.txt_title_offer.setText(item.title)
        holder?.txt_id_offer.setText(item.id.toString())
        holder?.txt_time_offer.setText(item.updated_time)
        if(item.banner_image != null){
//            Glide.with(context).load(item.banner_image).into(holder?.img_left_offer!!)
            Image.loadImageGlide(context,item.banner_image!!,holder?.img_left_offer!!,holder?.prg_offer_image!!)
        }
        holder?.img_left_offer!!.scaleType = ImageView.ScaleType.CENTER_CROP
        holder?.layout_item_offer.setOnClickListener(View.OnClickListener {
            clickItemListener(item,position)
        })
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val txt_title_offer = view.txt_title_offer!!
        val txt_id_offer = view.txt_id_offer!!
        val txt_time_offer = view.txt_time_offer!!
        val layout_status_offer = view.layout_status_offer
        val txt_status_offer = view.txt_status_offer
        val layout_item_offer = view.layout_item_offer
        val img_left_offer = view.img_left_offer
        val prg_offer_image = view.prg_offer_image
    }
}