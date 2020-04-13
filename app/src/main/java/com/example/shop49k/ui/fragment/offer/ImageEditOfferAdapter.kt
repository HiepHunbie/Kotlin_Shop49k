package com.example.shop49k.ui.fragment.offer

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.shop49k.R
import com.example.shop49k.model.image.ImageEditOfferModel
import kotlinx.android.synthetic.main.item_image_edit_offer.view.*

class ImageEditOfferAdapter (val context: Activity, val clickDeleteListener: (ImageEditOfferModel, Int) -> Unit): RecyclerView.Adapter<ImageEditOfferAdapter.ViewHolder>(){
    private var items: List<ImageEditOfferModel> = listOf()

    override fun getItemCount(): Int {
        return items.size

    }

    fun updatePosts(items: List<ImageEditOfferModel>, isLoadMore:Boolean) {
        if(isLoadMore){
            this.items+=items
        }else{
            this.items = items
        }
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_edit_offer, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ImageEditOfferModel = items[position]

        if(item.path != null){
            Glide.with(context).load(item.path).into(holder?.img_image)
        }else{
            holder?.img_image.setImageBitmap(item.bitmap)
        }
        holder?.img_image!!.scaleType = ImageView.ScaleType.CENTER_CROP
//        holder?.img_image.setBackgroundResource(item.path)

        if(item.type == 0){
            holder?.layout_type_image.visibility = View.VISIBLE
        }else{
            holder?.layout_type_image.visibility = View.GONE
        }

        holder?.img_delete_item.setOnClickListener(View.OnClickListener {
            clickDeleteListener(item,position)
        })
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val img_image = view.img_image!!
        val img_delete_item = view.img_delete_item
        val layout_type_image = view.layout_type_image
    }
}