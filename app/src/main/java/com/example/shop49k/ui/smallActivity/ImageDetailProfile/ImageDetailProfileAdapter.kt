package com.example.shop49k.ui.smallActivity.ImageDetailProfile

import android.app.Activity
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.shop49k.R
import com.example.shop49k.model.image.ImageDetailProfileModel
import com.example.shop49k.ui.main.MainActivity
import kotlinx.android.synthetic.main.item_add_iamge_detail.view.*
import kotlinx.android.synthetic.main.item_image_view_detail.view.*
import javax.sql.DataSource

class ImageDetailProfileAdapter (val context: Activity
                                 , val clickAddImage: (ImageDetailProfileModel, Int) -> Unit
                                 , val clickItemImage: (ImageDetailProfileModel, Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ADD_IMAGE = 0
        private const val TYPE_IMAGE_DETAIL = 1
    }
    // Gets the number of animals in the list

    private var items: List<ImageDetailProfileModel> = listOf()

    override fun getItemCount(): Int {
        return items.size

    }
    override fun getItemViewType(position: Int): Int {
        val model = items[position].type
        return when (model) {
            0 -> TYPE_ADD_IMAGE
            1 -> TYPE_IMAGE_DETAIL
            else -> TYPE_IMAGE_DETAIL
        }
    }
    fun updatePosts(items: List<ImageDetailProfileModel>,isLoadMore:Boolean) {
        if(isLoadMore){
            this.items+=items
        }else{
            this.items = items
        }
        notifyDataSetChanged()
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ADD_IMAGE -> {
                return ViewAddImageHolder(LayoutInflater.from(context).inflate(R.layout.item_add_iamge_detail, parent, false))
            }
            TYPE_IMAGE_DETAIL -> {
                return ViewImageDetailHolder(LayoutInflater.from(context).inflate(R.layout.item_image_view_detail, parent, false))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    // Binds each animal in the ArrayList to a view

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewAddImageHolder -> {

                holder?.layout_add_image_detail.setOnClickListener(View.OnClickListener {
                    clickAddImage(items[position],position)
                })
            }
            is ViewImageDetailHolder -> {
                val item = items[position]
                if(item.image_drawable != null){
                    holder?.prg_image_detail.visibility = View.VISIBLE
                    Glide.with(context).load(item.image_drawable)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                holder?.prg_image_detail.visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                dataSource: com.bumptech.glide.load.DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                holder?.prg_image_detail.visibility = View.GONE
                                return false
                            }

                        }).into(holder?.img_image_view_detail)
                }else{
                    holder?.img_image_view_detail.setImageBitmap(item.bitmap)
                }
                holder?.img_image_view_detail.setOnClickListener(View.OnClickListener {
                    clickItemImage(item,position)
                })
                holder?.img_image_view_detail!!.scaleType = ImageView.ScaleType.CENTER_CROP
            }
            else -> throw IllegalArgumentException()
        }

    }

    class ViewAddImageHolder(view: View) : RecyclerView.ViewHolder(view) {
       val layout_add_image_detail = view.layout_add_image_detail
    }

    class ViewImageDetailHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img_image_view_detail = view.img_image_view_detail!!
        val prg_image_detail = view.prg_image_detail
    }
}