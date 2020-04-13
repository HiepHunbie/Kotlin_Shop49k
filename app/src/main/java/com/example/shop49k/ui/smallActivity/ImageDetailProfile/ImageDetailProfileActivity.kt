package com.example.shop49k.ui.smallActivity.ImageDetailProfile

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.shop49k.R
import com.example.shop49k.base.BaseActivity
import com.example.shop49k.model.image.ImageDetailProfileModel
import com.example.shop49k.model.image.updateImage.*
import com.example.shop49k.model.image.uploadImage.ImageUploadResult
import com.example.shop49k.utils.`object`.Dialogs
import com.example.shop49k.utils.`object`.GetImageUtil
import com.example.shop49k.utils.GridItemDecoration
import com.example.shop49k.utils.TOKEN
import kotlinx.android.synthetic.main.dialog_image_detail.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ImageDetailProfileActivity : BaseActivity<ImageDetailProfilePresenter>(), ImageDetailProfileView {
    override fun addImageSuccess(forgotResult: UpdateImageResult) {
        Dialogs.showToastInformation(this,getString(R.string.add_image_success))
        listImage.clear()
        listImage.add(ImageDetailProfileModel(null,null,0,0))
        for(item in forgotResult.data){
            listImage.add(ImageDetailProfileModel(item.url,null,1, item.id.toInt()))
        }
        imageDetailProfileAdapter!!.updatePosts(listImage,false)
        prg_image_detail_profile.visibility = View.GONE
    }

    override fun addImageError(forgotResult: String) {
        prg_image_detail_profile.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(this!!,forgotResult)
    }

    override fun deleteImageSuccess(forgotResult: UpdateImageResult) {
        Dialogs.showToastInformation(this,getString(R.string.delete_image_success))
        listImage.clear()
        listImage.add(ImageDetailProfileModel(null,null,0,0))
        for(item in forgotResult.data){
            listImage.add(ImageDetailProfileModel(item.url,null,1, item.id.toInt()))
        }
        imageDetailProfileAdapter!!.updatePosts(listImage,false)
        prg_image_detail_profile.visibility = View.GONE
    }

    override fun deleteImageError(forgotResult: String) {
        prg_image_detail_profile.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(this!!,forgotResult)
    }

    override fun uploadSuccess(forgotResult: ImageUploadResult) {
//        Dialogs.showToastInformation(this,forgotResult.data!![0].url)
        var updateImageInput = UpdateImageInput(listOf(UserImages("add",listOf(Images(forgotResult.data!![0].name,forgotResult.data!![0].url)))))
        presenter!!.addImage(this,prefs!!.getString(TOKEN,""),updateImageInput)

    }

    override fun uploadError(forgotResult: String) {
        prg_image_detail_profile.visibility = View.GONE
        Dialogs.showToastErrorNewNotListenerClick(this!!,forgotResult)
    }

    override fun instantiatePresenter(): ImageDetailProfilePresenter {
        return ImageDetailProfilePresenter(this)
    }

    var listImage : ArrayList<ImageDetailProfileModel> = ArrayList()
    var imageDetailProfileAdapter : ImageDetailProfileAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_image_detail)

        listImage.add(ImageDetailProfileModel(null,null,0,0))
        for(item in dataUserInfo!!.data!!.user_images){
            listImage.add(ImageDetailProfileModel(item.url,null,1, item.id))
        }

        imageDetailProfileAdapter = ImageDetailProfileAdapter(this!!, { itemDto: ImageDetailProfileModel, position: Int ->
            Dialogs.showDialogBottomSelectPicture(this!!,{ dialogNew: Dialog, pos : Int ->
                if(pos == 0){
                    GetImageUtil.captureImage(listImage!!.size.toString(),this)
                }else if(pos == 1){
                    GetImageUtil.openAlbum(this)
                }
                dialogNew.dismiss()
            })
        },{itemDto: ImageDetailProfileModel, position: Int ->
            Dialogs.showDialogViewImageDetail(this!!,itemDto,{ dialogNew: Dialog, imageDetailProfileModel:ImageDetailProfileModel ->
                Dialogs.showDialogDeleteImage(this,imageDetailProfileModel!!,{ dialogSmall: Dialog, itemDto : ImageDetailProfileModel ->
//                    listImage.remove(imageDetailProfileModel)
//                    imageDetailProfileAdapter!!.updatePosts(listImage,false)
                    var deleteImageInput = DeleteImageInput(listOf(UserImagesDelete("delete", listOf(itemDto.id))))
                    presenter!!.deleteImage(this,prefs!!.getString(TOKEN,""),deleteImageInput)
                    prg_image_detail_profile.visibility = View.VISIBLE
                    dialogSmall.dismiss()
                    dialogNew.dismiss()
                })
            })
    })
        ryv_image_detail_profile!!.adapter = imageDetailProfileAdapter
        ryv_image_detail_profile!!.layoutManager = GridLayoutManager(this, 3) as RecyclerView.LayoutManager?

        ryv_image_detail_profile.addItemDecoration(GridItemDecoration(resources.getDimensionPixelOffset(R.dimen._10sdp), 3))
        imageDetailProfileAdapter!!.updatePosts(listImage,false)

        img_back_image_detail_profile.setOnClickListener(View.OnClickListener {
            finish()
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GetImageUtil.FINAL_TAKE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(GetImageUtil.imageUri!!))
                    var file = File(GetImageUtil.imageUri!!.path)
                    var fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
                    var filePart = MultipartBody.Part.createFormData("file[]", file.getName(), fileReqBody)
                    var description = RequestBody.create(MediaType.parse("text/plain"), "image-type")
                    prg_image_detail_profile.visibility = View.VISIBLE
                    presenter!!.uploadImage(this,prefs!!.getString(TOKEN, ""),filePart,fileReqBody)
//                    listImage.add(ImageDetailProfileModel(null,bitmap,1))
//                    imageDetailProfileAdapter!!.updatePosts(listImage,false)
                }
            GetImageUtil.FINAL_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
//                        4.4以上
                        var filePath = GetImageUtil.handleImageOnKitkat(data,this)!!
                        var file = File(filePath)
                        var fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
                        var filePart = MultipartBody.Part.createFormData("file[]", file.getName(), fileReqBody)
                        var description = RequestBody.create(MediaType.parse("text/plain"), "image-type")
                        prg_image_detail_profile.visibility = View.VISIBLE
                        presenter!!.uploadImage(this,prefs!!.getString(TOKEN, ""),filePart,fileReqBody)
                    }
                    else{
//                        4.4以下
                        GetImageUtil.handleImageBeforeKitkat(data)
                    }
                }
        }
    }
}