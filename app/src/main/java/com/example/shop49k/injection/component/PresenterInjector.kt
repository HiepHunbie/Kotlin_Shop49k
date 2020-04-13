package com.example.shop49k.injection.component

import com.example.shop49k.base.BaseView
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
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {
    /**
     * Injects required dependencies into the specified PostPresenter.
     * @param postPresenter PostPresenter in which to inject the dependencies
     */

    fun inject(loginPresenter: LoginPresenter)
    fun inject(mainPresenter: MainPresenter)
    fun inject(offerPresenter: OfferPresenter)
    fun inject(editOfferPresenter: EditOfferPresenter)
    fun inject(createOfferPresenter: CreateOfferPresenter)
    fun inject(forgotPassWordPresenter: ForgotPassWordPresenter)
    fun inject(signUpPresenter: SignUpPresenter)
    fun inject(imageDetailProfilePresenter: ImageDetailProfilePresenter)
    fun inject(profilePresenter: ProfilePresenter)
    fun inject(personInfoPresenter: PersonInfoPresenter)
    fun inject(shopInfoPresenter: ShopInfoPresenter)
    fun inject(salePresenter: SalePresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}