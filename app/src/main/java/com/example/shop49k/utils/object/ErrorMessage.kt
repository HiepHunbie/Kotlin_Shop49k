package com.example.shop49k.utils.`object`

import android.content.Context
import okhttp3.ResponseBody
import org.json.JSONObject

object ErrorMessage {

    fun getErrorMessage(responseBody: ResponseBody, context: Context): String {
        try {
            var aa = responseBody.string()
            val jsonObject = JSONObject(aa)
            var mess = jsonObject.getJSONObject("errors")
            return mess.toString()
        } catch (e: Exception) {
            return e.message.toString()
        }

    }

    fun getErrorMessageCode(responseBody: ResponseBody): String {
        return try {
            var aa = responseBody.string()
            val jsonObject = JSONObject(aa)
            jsonObject.getString("messageCode")
        } catch (e: Exception) {
            e.message.toString()
        }

    }

}