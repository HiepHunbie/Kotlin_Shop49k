package com.example.shop49k.utils.`object`

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.widget.ImageView
import com.example.shop49k.R
import java.io.File


object GetImageUtil {
     val FINAL_TAKE_PHOTO = 1
     val FINAL_CHOOSE_PHOTO = 2
    private var picture: ImageView? = null
     var imageUri: Uri? = null
     fun captureImage(name : String, activity: Activity){
        val outputImage = File(activity.externalCacheDir, name + ".jpg")
        if(outputImage.exists()) {
            outputImage.delete()
        }
        outputImage.createNewFile()
        imageUri = if(Build.VERSION.SDK_INT >= 24){
            FileProvider.getUriForFile(activity, activity.getString(R.string.pagkagename_provider), outputImage)
        } else {
            Uri.fromFile(outputImage)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        activity.startActivityForResult(intent, FINAL_TAKE_PHOTO)
    }
     fun openAlbum(activity: Activity){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        activity.startActivityForResult(intent, FINAL_CHOOSE_PHOTO)
    }
    @TargetApi(19)
    fun handleImageFromCameraOnKitkat(data: Uri,activity: Activity):String {
        var imagePath: String? = null
        val uri = data
        if (DocumentsContract.isDocumentUri(activity, uri)){
//            document类型的Uri，用document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority){
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = imagePath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion,
                    activity
                )
            }
            else if ("com.android.providers.downloads.documents" == uri.authority){
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = imagePath(contentUri, null, activity)
            }
        }
        else if ("content".equals(uri.scheme, ignoreCase = true)){
//            content类型Uri 普通方式处理
            imagePath = imagePath(uri, null, activity)
        }
        else if ("file".equals(uri.scheme, ignoreCase = true)){
            imagePath = uri.path
        }
        return displayImage(imagePath, activity)
    }

    @TargetApi(19)
     fun handleImageOnKitkat(data: Intent?,activity: Activity):String {
        var imagePath: String? = null
        val uri = data!!.data
        if (DocumentsContract.isDocumentUri(activity, uri)){
//            document类型的Uri，用document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority){
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = imagePath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion,
                    activity
                )
            }
            else if ("com.android.providers.downloads.documents" == uri.authority){
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = imagePath(contentUri, null, activity)
            }
        }
        else if ("content".equals(uri.scheme, ignoreCase = true)){
//            content类型Uri 普通方式处理
            imagePath = imagePath(uri, null, activity)
        }
        else if ("file".equals(uri.scheme, ignoreCase = true)){
            imagePath = uri.path
        }
        return displayImage(imagePath, activity)
    }

    //    没4.4的设备，略过
     fun handleImageBeforeKitkat(data: Intent?) {}

     fun imagePath(uri: Uri?, selection: String?,activity: Activity): String {
        var path: String? = null
//        通过Uri和selection获取路径
        val cursor = activity.contentResolver.query(uri, null, selection, null, null )
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

     fun displayImage(imagePath: String?,activity: Activity):String{
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            picture?.setImageBitmap(bitmap)

            return imagePath
        }
        else {
//            Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show()
            return ""
        }
    }

     fun getPathDeprecated(ctx: Context, uri: Uri?): String? {
        if (uri == null) {
            return null
        }
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = ctx.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        }
        return uri.path
    }

    fun getImageFilePath(context: Context, uri: Uri):String {

        var cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        var image_id = cursor.getString(0)
        image_id = image_id.substring(image_id.lastIndexOf(":") + 1)
        cursor.close()
        cursor = context.contentResolver.query(
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Images.Media._ID + " = ? ",
            arrayOf(image_id),
            null
        )
        cursor!!.moveToFirst()
        var path = ""
         path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()
        return  path
    }
}