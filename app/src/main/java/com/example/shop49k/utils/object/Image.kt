package com.example.shop49k.utils.`object`

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.shop49k.R

object Image {
    fun loadImageGlide(context:Context,path : String, imageView : ImageView, progressBar: ProgressBar){
        progressBar.visibility = View.VISIBLE
        Glide.with(context).load(path).apply( RequestOptions().placeholder(R.drawable.ic_error_image_loaded).error(R.drawable.ic_error_image_loaded)).listener(object :
            RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar!!.visibility = View.GONE
//                imageView.visibility = View.VISIBLE
//                imageView.setImageResource(R.drawable.ic_back_1_sale_detail)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar!!.visibility = View.GONE
                return false
            }

        }).into(imageView!!)
    }
}