package com.example.shop49k.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.ui.login.LoginActivity

class SplashActivity : BaseActivity<SplashPresenter>(), SplashView {
    override fun instantiatePresenter(): SplashPresenter {
        return SplashPresenter(this)
    }

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}