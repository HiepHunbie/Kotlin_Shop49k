package com.example.shop49k.ui.fragment.profile

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.shop49k.R
import com.example.shop49k.base.BaseFragment
import com.example.shop49k.ui.fragment.offer.OfferPresenter
import com.example.shop49k.ui.fragment.offer.OfferView
import com.example.shop49k.ui.login.LoginActivity
import com.example.shop49k.ui.main.MainActivity
import com.example.shop49k.ui.smallActivity.ImageDetailProfile.ImageDetailProfileActivity
import com.example.shop49k.ui.smallActivity.createOffer.CreateOfferActivity
import com.example.shop49k.ui.smallActivity.personInfo.PersonInfoActivity
import com.example.shop49k.ui.smallActivity.shopInfo.ShopInfoActivity
import com.example.shop49k.utils.`object`.Dialogs
import com.example.shop49k.utils.`object`.GetImageUtil
import com.example.shop49k.utils.`object`.SettingUtil
import kotlinx.android.synthetic.main.dialog_person_info.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import pl.aprilapps.easyphotopicker.EasyImage
import java.nio.file.Files.delete
import pl.aprilapps.easyphotopicker.DefaultCallback
import android.R.attr.data
import android.graphics.drawable.Drawable
import java.io.File
import android.support.v4.app.Fragment
import android.widget.*
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.shop49k.model.changePassword.ChangePasswordInput
import com.example.shop49k.model.changePassword.ChangePasswordResult
import com.example.shop49k.model.image.uploadImage.ImageUploadResult
import com.example.shop49k.model.personInfo.AvatarInput
import com.example.shop49k.model.personInfo.PersonInforResult
import com.example.shop49k.model.userInfo.UserInfo
import com.example.shop49k.utils.*
import com.example.shop49k.utils.`object`.Image
import com.jackandphantom.circularimageview.RoundedImage
import kotlinx.android.synthetic.main.dialog_image_detail.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody


class ProfileFragment : BaseFragment<ProfilePresenter>(), ProfileView, MainActivity.OnBackPressedListner{
    override fun changePasswordSuccess(forgotResult: ChangePasswordResult, dialog:Dialog) {
        dialog.dismiss()
        SettingUtil.showDialogSuccessChangePassword(parentActivity!!,{ dialogNew: Dialog ->
            dialogNew.dismiss()
            val intent = Intent(parentActivity, LoginActivity::class.java)
//            intent.putExtra(IS_FIRST_SIGN_UP,false)
            val editor = parentActivity!!.prefs!!.edit()
            editor.putInt(TYPE_USER, 0)
            editor.putString(TOKEN, "")
            editor.putString(USER_NAME, "")
            editor.putString(PASSWORD, "")
            editor.apply()
            startActivity(intent)
            parentActivity!!.finish()
        })
    }

    override fun changePasswordError(forgotResult: String) {
        Dialogs.showToastErrorNewNotListenerClick(parentActivity!!,forgotResult)
    }

    override fun checkOldPasswordSuccess(forgotResult: ChangePasswordResult, dialog:Dialog) {
        dialog.dismiss()
        SettingUtil.showDialogAddNewPassword(parentActivity!!,{ dialogAddNewPassword: Dialog, pass : String, confirmPass : String ->
            changePasswordInput!!.new_password = pass
            changePasswordInput!!.new_password_confirm = confirmPass
            presenter!!.changePassword(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN,""),changePasswordInput!!, dialogAddNewPassword)
        })
    }

    override fun checkOldPasswordError(forgotResult: String) {
    }

    override fun updateUserSuccess(forgotResult: PersonInforResult) {
        presenter!!.getUserInfo(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""))
        prg_profile!!.visibility = View.GONE
    }

    override fun updateUserError(forgotResult: String) {
        prg_profile!!.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(parentActivity!!,forgotResult)
    }

    override fun uploadSuccess(forgotResult: ImageUploadResult) {
        var avatarInput = AvatarInput(forgotResult.data[0].url)
        presenter!!.updateAvatar(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""),avatarInput!!)
        Image.loadImageGlide(context,forgotResult.data[0].url,img_person_avatar!!,prg_profile_avatar!!)
    }

    override fun uploadError(forgotResult: String) {
        prg_profile!!.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(parentActivity!!,forgotResult)
    }

    override fun getUserInfoSuccess(userInfo: UserInfo) {
        prg_profile!!.visibility = View.GONE
        val editor = parentActivity!!.prefs!!.edit()
        editor.putString(DATA_USER_INFO, parentActivity!!.gson!!.toJson(userInfo))
        editor.apply()
        parentActivity!!.dataUserInfo = parentActivity!!.gson.fromJson(parentActivity!!.prefs!!.getString(DATA_USER_INFO,"").toString(), UserInfo::class.java)
        setImages()
    }

    override fun errorGetUserInfo(mess: String) {
        prg_profile!!.visibility = View.GONE
    }


    override fun onBackPressedFragment(): Boolean {
        parentActivity!!.addFragment(0)
        return false
    }

    override fun instantiatePresenter(): ProfilePresenter {
        return ProfilePresenter(this)    }

    override fun getContext(): Context {
        return parentActivity!!.getContext()
    }

    var txt_view_all_profile : TextView? = null
    var layout_person_profile : RelativeLayout? = null
    var layout_shop_profile : RelativeLayout? = null
    var layout_setting_profile : RelativeLayout? = null
    var layout_contact : RelativeLayout? = null
    var img_person_avatar:de.hdodenhof.circleimageview.CircleImageView? = null
    var img_1_profile:RoundedImage? = null
    var img_2_profile:RoundedImage? = null
    var img_3_profile:RoundedImage? = null
    var img_4_profile:RoundedImage? = null
    var img_back_4_profile : RoundedImage? = null
    var txt_name_profile : TextView? = null
    var txt_id_profile : TextView? = null
    var txt_num_image_more_profile : TextView? = null
    var layout_avatar : RelativeLayout? = null
    var prg_profile : ProgressBar? = null
    var layout_1_profile : RelativeLayout? = null
    var layout_2_profile : RelativeLayout? = null
    var layout_3_profile : RelativeLayout? = null
    var layout_4_profile : RelativeLayout? = null
    var prg_profile_avatar : ProgressBar? = null
    var prg_1_profile : ProgressBar? = null
    var prg_2_profile : ProgressBar? = null
    var prg_3_profile : ProgressBar? = null
    var prg_4_profile : ProgressBar? = null
    var txt_status_account : TextView? = null
    var changePasswordInput : ChangePasswordInput? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        changePasswordInput = ChangePasswordInput("","","")
        txt_view_all_profile = view.txt_view_all_profile
        layout_person_profile = view.layout_person_profile
        layout_shop_profile = view.layout_shop_info
        layout_setting_profile = view.layout_setting
        layout_contact = view.layout_contact
        img_person_avatar = view.img_person_avatar
        img_1_profile = view.img_1_profile
        img_2_profile = view.img_2_profile
        img_3_profile = view.img_3_profile
        img_4_profile = view.img_4_profile
        layout_1_profile = view.layout_1_profile
        layout_2_profile = view.layout_2_profile
        layout_3_profile = view.layout_3_profile
        layout_4_profile = view.layout_4_profile
        prg_profile_avatar = view.prg_profile_avatar
        img_1_profile = view.img_1_profile
        prg_1_profile = view.prg_1_profile
        prg_2_profile = view.prg_2_profile
        prg_3_profile = view.prg_3_profile
        prg_4_profile = view.prg_4_profile
        txt_status_account = view.txt_status_account
        img_back_4_profile = view.img_back_4_profile
        txt_num_image_more_profile = view.txt_num_image_more_profile
        layout_avatar = view.layout_avatar
        prg_profile = view.prg_profile

        img_1_profile!!.scaleType = ImageView.ScaleType.CENTER_CROP
        img_2_profile!!.scaleType = ImageView.ScaleType.CENTER_CROP
        img_3_profile!!.scaleType = ImageView.ScaleType.CENTER_CROP
        img_4_profile!!.scaleType = ImageView.ScaleType.CENTER_CROP

        txt_name_profile = view.txt_name_profile
        txt_id_profile = view.txt_id_profile

        txt_name_profile!!.setText(parentActivity!!.dataUserInfo!!.data.fullname)
        if(parentActivity!!.dataUserInfo!!.data.merchant_short_code != null){
            txt_id_profile!!.setText(parentActivity!!.getString(R.string.merchant_id) + " : "+parentActivity!!.dataUserInfo!!.data.merchant_short_code)
        }else{
            txt_id_profile!!.setText(parentActivity!!.getString(R.string.merchant_id) + " : ")
        }

        if(parentActivity!!.dataUserInfo!!.data.avatar != null){
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.avatar,img_person_avatar!!,prg_profile_avatar!!)
        }
        setImages()
        img_person_avatar!!.setOnClickListener(View.OnClickListener {
            Dialogs.showDialogBottomSelectPicture(parentActivity!!,{ dialogNew: Dialog, pos : Int ->
                if(pos == 0){
                    EasyImage.openCameraForImage( fragmentManager!!.findFragmentById(R.id.container_frame),1)
                }else if(pos == 1){
//                    GetImageUtil.openAlbum(parentActivity!!)
                    EasyImage.openGallery(fragmentManager!!.findFragmentById(R.id.container_frame),1)
                }
                dialogNew.dismiss()
            })
        })
        layout_avatar!!.setOnClickListener(View.OnClickListener {
            Dialogs.showDialogBottomSelectPicture(parentActivity!!,{ dialogNew: Dialog, pos : Int ->
                if(pos == 0){
                    EasyImage.openCameraForImage( fragmentManager!!.findFragmentById(R.id.container_frame),1)
                }else if(pos == 1){
//                    GetImageUtil.openAlbum(parentActivity!!)
                    EasyImage.openGallery(fragmentManager!!.findFragmentById(R.id.container_frame),1)
                }
                dialogNew.dismiss()
            })
        })

        txt_view_all_profile!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(parentActivity!!.applicationContext, ImageDetailProfileActivity::class.java)
            startActivity(intent)
        })

        layout_person_profile!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(parentActivity!!.applicationContext, PersonInfoActivity::class.java)
            startActivity(intent)
        })
        layout_shop_profile!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(parentActivity!!.applicationContext, ShopInfoActivity::class.java)
            startActivity(intent)
        })

        layout_setting_profile!!.setOnClickListener(View.OnClickListener {
            SettingUtil.showDialogSetting(parentActivity!!,{ dialogSetting: Dialog ->
                dialogSetting.dismiss()
            },{ dialogSetting: Dialog ->
                SettingUtil.showDialogAddOldPassword(parentActivity!!,{ dialogAddOldPassword: Dialog ->
                    dialogAddOldPassword.dismiss()
                },{ dialogAddOldPassword: Dialog, pass : String ->
                    changePasswordInput!!.old_password = pass
                    changePasswordInput!!.new_password = pass
                    changePasswordInput!!.new_password_confirm = pass
                    presenter!!.checkOldPassword(parentActivity!!, parentActivity!!.prefs!!.getString(TOKEN,""),changePasswordInput!!,dialogAddOldPassword)
                })
            },{ dialogSetting: Dialog, isChecked : Boolean ->
            },{ dialogSetting: Dialog, isChecked : Boolean ->
            },{ dialogSetting: Dialog ->
                val editor = parentActivity!!.prefs!!.edit()
                editor.putInt(TYPE_USER, 0)
                editor.putString(TOKEN, "")
                editor.putString(USER_NAME, "")
                editor.putString(PASSWORD, "")
                editor.apply()
                val intent = Intent(parentActivity!!, LoginActivity::class.java)
                startActivity(intent)
                parentActivity!!.finish()
            })
        })

        layout_contact!!.setOnClickListener(View.OnClickListener {
            SettingUtil.showDialogContact(parentActivity!!,{ dialogContact: Dialog ->
                dialogContact.dismiss()
            })
        })
        return view
    }

    fun setImages(){
        if(parentActivity!!.dataUserInfo!!.data.user_images.size == 1){
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[0].url,img_1_profile!!,prg_1_profile!!)
            img_1_profile!!.visibility = View.VISIBLE
            img_2_profile!!.visibility = View.GONE
            img_3_profile!!.visibility = View.GONE
            img_4_profile!!.visibility = View.GONE
            img_back_4_profile!!.visibility = View.GONE
            txt_num_image_more_profile!!.visibility = View.GONE
        }else if(parentActivity!!.dataUserInfo!!.data.user_images.size == 2){
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[0].url,img_1_profile!!,prg_1_profile!!)
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[1].url,img_2_profile!!,prg_2_profile!!)
            img_1_profile!!.visibility = View.VISIBLE
            img_2_profile!!.visibility = View.VISIBLE
            img_3_profile!!.visibility = View.GONE
            img_4_profile!!.visibility = View.GONE
            img_back_4_profile!!.visibility = View.GONE
            txt_num_image_more_profile!!.visibility = View.GONE
        }else if(parentActivity!!.dataUserInfo!!.data.user_images.size == 3){
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[0].url,img_1_profile!!,prg_1_profile!!)
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[1].url,img_2_profile!!,prg_2_profile!!)
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[2].url,img_3_profile!!,prg_3_profile!!)
            img_1_profile!!.visibility = View.VISIBLE
            img_2_profile!!.visibility = View.VISIBLE
            img_3_profile!!.visibility = View.VISIBLE
            img_4_profile!!.visibility = View.GONE
            img_back_4_profile!!.visibility = View.GONE
            txt_num_image_more_profile!!.visibility = View.GONE
        }else if(parentActivity!!.dataUserInfo!!.data.user_images.size == 4){
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[0].url,img_1_profile!!,prg_1_profile!!)
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[1].url,img_2_profile!!,prg_2_profile!!)
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[2].url,img_3_profile!!,prg_3_profile!!)
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[3].url,img_4_profile!!,prg_4_profile!!)
            img_1_profile!!.visibility = View.VISIBLE
            img_2_profile!!.visibility = View.VISIBLE
            img_3_profile!!.visibility = View.VISIBLE
            img_4_profile!!.visibility = View.VISIBLE
            img_back_4_profile!!.visibility = View.GONE
            txt_num_image_more_profile!!.visibility = View.GONE
        }else if(parentActivity!!.dataUserInfo!!.data.user_images.size > 4){
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[0].url,img_1_profile!!,prg_1_profile!!)
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[1].url,img_2_profile!!,prg_2_profile!!)
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[2].url,img_3_profile!!,prg_3_profile!!)
            Image.loadImageGlide(context,parentActivity!!.dataUserInfo!!.data.user_images[3].url,img_4_profile!!,prg_4_profile!!)
            img_1_profile!!.visibility = View.VISIBLE
            img_2_profile!!.visibility = View.VISIBLE
            img_3_profile!!.visibility = View.VISIBLE
            img_4_profile!!.visibility = View.VISIBLE
            img_back_4_profile!!.visibility = View.VISIBLE
            txt_num_image_more_profile!!.visibility = View.VISIBLE
            var moreCount = parentActivity!!.dataUserInfo!!.data.user_images.size - 4
            txt_num_image_more_profile!!.setText("+"+moreCount.toString())
        }else {
            layout_1_profile!!.visibility = View.GONE
            layout_2_profile!!.visibility = View.GONE
            layout_3_profile!!.visibility = View.GONE
            layout_4_profile!!.visibility = View.GONE
            img_1_profile!!.visibility = View.GONE
            img_2_profile!!.visibility = View.GONE
            img_3_profile!!.visibility = View.GONE
            img_4_profile!!.visibility = View.GONE
            img_back_4_profile!!.visibility = View.GONE
            txt_num_image_more_profile!!.visibility = View.GONE
        }

        if(parentActivity!!.dataUserInfo!!.data.user_images.size == 0){
            txt_view_all_profile!!.setText(parentActivity!!.getString(R.string.upload_image))
        }else{
            txt_view_all_profile!!.setText(parentActivity!!.getString(R.string.view_all))
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
        }
        EasyImage.handleActivityResult(requestCode, resultCode, data, parentActivity, object : DefaultCallback() {
            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
                e!!.printStackTrace()
            }

            override fun onImagesPicked(imageFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
//                onPhotosReturned(imageFiles)
                var bitmap = BitmapFactory.decodeFile(imageFiles[0].path)
                var file = File(imageFiles[0].path)
                var fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
                var filePart = MultipartBody.Part.createFormData("file[]", file.getName(), fileReqBody)
                var description = RequestBody.create(MediaType.parse("text/plain"), "image-type")
                prg_profile!!.visibility = View.VISIBLE
                presenter!!.uploadImage(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""),filePart,fileReqBody)
//                img_person_avatar!!.setImageBitmap(bitmap)
            }

            override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA_IMAGE) {
                    val photoFile = EasyImage.lastlyTakenButCanceledPhoto(parentActivity!!)
                    photoFile?.delete()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        presenter!!.getUserInfo(parentActivity!!,parentActivity!!.prefs!!.getString(TOKEN, ""))
        prg_profile!!.visibility = View.VISIBLE
    }
}