package com.example.shop49k.base


import com.example.shop49k.injection.component.DaggerPresenterInjector
import com.example.shop49k.injection.component.PresenterInjector
import com.example.shop49k.injection.module.ContextModule
import com.example.shop49k.injection.module.NetworkModule
import com.example.shop49k.ui.forgotPassword.ForgotPassWordPresenter
import com.example.shop49k.ui.fragment.offer.OfferPresenter
import com.example.shop49k.ui.fragment.profile.ProfilePresenter
import com.example.shop49k.ui.fragment.sale.SalePresenter
import com.example.shop49k.ui.login.LoginPresenter
import com.example.shop49k.ui.main.MainPresenter
import com.example.shop49k.ui.signup.SignUpPresenter
import com.example.shop49k.ui.smallActivity.ImageDetailProfile.ImageDetailProfilePresenter
import com.example.shop49k.ui.smallActivity.createOffer.CreateOfferPresenter
import com.example.shop49k.ui.smallActivity.editOffer.EditOfferPresenter
import com.example.shop49k.ui.smallActivity.personInfo.PersonInfoPresenter
import com.example.shop49k.ui.smallActivity.shopInfo.ShopInfoPresenter

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 * @param V the type of the View the presenter is based on
 * @property view the view the presenter is based on
 * @property injector The injector used to inject required dependencies
 * @constructor Injects the required dependencies
 */
abstract class BasePresenter<out V : com.example.shop49k.base.BaseView>(protected val view: V) {
    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .baseView(view)
        .contextModule(ContextModule)
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * This method may be called when the presenter view is created
     */
    open fun onViewCreated(){}

    /**
     * This method may be called when the presenter view is destroyed
     */
    open fun onViewDestroyed(){}

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is LoginPresenter -> injector.inject(this)
            is MainPresenter -> injector.inject(this)
            is OfferPresenter -> injector.inject(this)
            is EditOfferPresenter -> injector.inject(this)
            is CreateOfferPresenter -> injector.inject(this)
            is ForgotPassWordPresenter -> injector.inject(this)
            is SignUpPresenter -> injector.inject(this)
            is ImageDetailProfilePresenter -> injector.inject(this)
            is ProfilePresenter -> injector.inject(this)
            is PersonInfoPresenter -> injector.inject(this)
            is ShopInfoPresenter -> injector.inject(this)
            is SalePresenter -> injector.inject(this)
        }
    }
}