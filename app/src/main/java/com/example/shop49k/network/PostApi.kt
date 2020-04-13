package com.example.shop49k.network

import com.example.shop49k.model.changePassword.ChangePasswordInput
import com.example.shop49k.model.changePassword.ChangePasswordResult
import com.example.shop49k.model.city.CityDetail
import com.example.shop49k.model.forgotPassword.ForgotCompleteInput
import com.example.shop49k.model.forgotPassword.ForgotInput
import com.example.shop49k.model.forgotPassword.ForgotResult
import com.example.shop49k.model.image.updateImage.DeleteImageInput
import com.example.shop49k.model.image.updateImage.UpdateImageInput
import com.example.shop49k.model.image.updateImage.UpdateImageResult
import com.example.shop49k.model.image.uploadImage.ImageUploadResult
import com.example.shop49k.model.userInfo.UserInfo
import com.example.shop49k.model.login.LoginData
import com.example.shop49k.model.login.LoginInput
import com.example.shop49k.model.masterData.MasterData
import com.example.shop49k.model.offer.OfferDetail
import com.example.shop49k.model.offer.addAddress.AddAddressInput
import com.example.shop49k.model.offer.createOffer.OfferCreateInput
import com.example.shop49k.model.offer.createOffer.OfferCreateResult
import com.example.shop49k.model.offer.editOffer.OfferInput
import com.example.shop49k.model.offer.editOffer.OfferResult
import com.example.shop49k.model.offer.offerDetail.OfferData
import com.example.shop49k.model.personInfo.AvatarInput
import com.example.shop49k.model.personInfo.PersonInfoInput
import com.example.shop49k.model.personInfo.PersonInforResult
import com.example.shop49k.model.register.RegisterInput
import com.example.shop49k.model.register.RegisterResult
import com.example.shop49k.model.sale.SaleDetail
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


/**
 * The interface which provides methods to get result of webservices
 */
interface PostApi {

    //LoginActivity
    @Headers("Content-Type: application/json")
    @POST("api/login")
    fun login(@Body body: LoginInput): Observable<LoginData>

    @GET("api/user_info")
    fun getUserInfo( @Header("Authorization") token: String): Observable<UserInfo>

    @GET("api/master_data")
    fun getMasterData(): Observable<MasterData>

    //MainActivity
    @GET("api/load_city")
    fun getCity(@Query("_type") type:String,
                @Query("q") q:String): Observable<CityDetail>

    @GET("api/load_district")
    fun getDistrict(@Query("_type") type:String,
                    @Query("q") q:String,
                    @Query("city_id") city_id:String): Observable<CityDetail>

    @GET("api/load_commune")
    fun getCommune(@Query("_type") type:String,
                    @Query("q") q:String,
                    @Query("district_id") district_id:String): Observable<CityDetail>

    @GET("api/offer")
    fun getOffers(@Header("Authorization") token: String,@Query("sort_field") type:String,
                   @Query("sort_order") district_id:String,
                  @Query("status") status:String,
                  @Query("limit") limit:String,
    @Query("page") page:String): Observable<OfferDetail>

    @GET("api/offer")
    fun getOffersWithId(@Header("Authorization") token: String,@Query("sort_field") type:String,
                        @Query("id") id:String,
                  @Query("sort_order") district_id:String): Observable<OfferData>

    //Offer
    @Headers("Content-Type: application/json")
    @PUT("api/offer")
    fun editOffer(@Header("Authorization") token: String
                       , @Body body: OfferInput): Observable<OfferResult>

    @Headers("Content-Type: application/json")
    @POST("api/offer")
    fun createOffer(@Header("Authorization") token: String
                  , @Body body: OfferCreateInput): Observable<OfferCreateResult>

    @Headers("Content-Type: application/json")
    @POST("api/offer_address")
    fun addAddress(@Header("Authorization") token: String
                  , @Body body: AddAddressInput): Observable<OfferResult>


    //Forgot Password
    @Headers("Content-Type: application/json")
    @POST("api/forgot_password")
    fun forgotPassword(@Body body: ForgotInput): Observable<ForgotResult>

    @Headers("Content-Type: application/json")
    @POST("api/forgot_password_complete")
    fun forgotPasswordComplete(@Body body: ForgotCompleteInput): Observable<ForgotResult>

    //Register
    @Headers("Content-Type: application/json")
    @POST("api/register")
    fun register(@Body body: RegisterInput): Observable<RegisterResult>

    @Headers("Content-Type: application/json")
    @POST("api/validate_user")
    fun validateUser(@Body body: RegisterInput): Observable<RegisterResult>

    //Upload Image
    @Multipart
    @POST("api/upload_image")
    fun uploadImage(@Header("Authorization") token: String,@Part file: MultipartBody.Part): Observable<ImageUploadResult>

    @Headers("Content-Type: application/json")
    @PUT("api/update_user_images")
    fun updateImage(@Header("Authorization") token: String
                  , @Body body: UpdateImageInput): Observable<UpdateImageResult>

    @Headers("Content-Type: application/json")
    @PUT("api/update_user_images")
    fun deleteImage(@Header("Authorization") token: String
                    , @Body body: DeleteImageInput): Observable<UpdateImageResult>

    //Person Info
    @Headers("Content-Type: application/json")
    @PUT("api/update_user")
    fun updatePersonInfo(@Header("Authorization") token: String
                    , @Body body: PersonInfoInput): Observable<PersonInforResult>

    @Headers("Content-Type: application/json")
    @PUT("api/update_user")
    fun updateAvatar(@Header("Authorization") token: String
                         , @Body body: AvatarInput): Observable<PersonInforResult>

    //Change password
    @Headers("Content-Type: application/json")
    @POST("api/change_password")
    fun changePassword(@Header("Authorization") token: String,@Body body: ChangePasswordInput): Observable<ChangePasswordResult>

    //Sale
    @GET("api/notifications")
    fun getListSale(): Observable<SaleDetail>

}