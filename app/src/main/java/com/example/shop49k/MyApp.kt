package com.example.shop49k

import android.app.Application
import com.example.shop49k.utils.TypefaceUtil
import com.facebook.drawee.backends.pipeline.Fresco

class MyApp : Application() {


    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this)
    }
}