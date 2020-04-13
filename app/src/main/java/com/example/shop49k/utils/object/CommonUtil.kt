package com.example.shop49k.utils.`object`

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.example.shop49k.R
import com.example.shop49k.model.chart.ChartDetail
import com.example.shop49k.model.error.ErrorType
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

object CommonUtil {

    fun setupLineChartDataAlert(lineChart:com.github.mikephil.charting.charts.LineChart, items: List<ChartDetail>, context: Context?) {
        val dataSets = ArrayList<ILineDataSet>()
        val yVals = ArrayList<Entry>()
        var totalTimeCheck = 0
        val yValsSetData = ArrayList<Entry>()
        var set1: LineDataSet
        for (i in items.indices){
//        for(item in items){
            val item = items[i]

            totalTimeCheck+=1
            yVals.add(Entry(totalTimeCheck.toFloat(), item.value.toFloat(), totalTimeCheck.toString()))
            for(item in yVals){
                if(item.data.toString().toInt() >= totalTimeCheck-1){
                    yValsSetData.add(item)
                }
            }
            set1 = LineDataSet(yValsSetData, "DataSet$i")

            set1.setCircleColor(context!!.resources.getColor(R.color.green_home))
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.setDrawCircles(false)
            set1.valueTextSize = 0f
            set1.setDrawFilled(true)
            set1.fillColor = context!!.resources.getColor(R.color.text_red_home)

            set1.color = context!!.resources.getColor(R.color.num_circle_text_units)
            val drawable = ContextCompat.getDrawable(context!!, R.drawable.fill_color_chart_green)
            set1.fillDrawable = drawable

            dataSets.add(set1)
        }

        creatLineChart(dataSets, lineChart)
    }

    private fun creatLineChart(dataSets : ArrayList<ILineDataSet>, lineChart: LineChart){
        var data = LineData()
        data = LineData(dataSets)
        // set dataDeviceAutoGen
        lineChart.data = data

        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.setPinchZoom(false)
        lineChart.setScaleEnabled(false)
        lineChart.setTouchEnabled(false)
        lineChart.data.isHighlightEnabled = false
        lineChart.isFocusableInTouchMode = false
        lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
        lineChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
        lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
//        if(isItem){
//            lineChart.axisRight.isEnabled = false
//            lineChart.axisLeft.isEnabled = false
//            lineChart.xAxis.isEnabled = false
//        }
        lineChart.axisLeft.setAxisMinValue(0f)
        lineChart.axisRight.setAxisMinValue(0f)
        lineChart.invalidate()
    }
    fun hideKeyboard(view: View, context: Context) {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun setColorImageSvg( activity: Activity,imageView: ImageView, color: Int, textView: TextView){
        DrawableCompat.setTint(imageView.getDrawable(), ContextCompat.getColor(activity.getApplicationContext(), color));
        textView.setTextColor(activity.resources.getColor(color))
    }
    fun isValidEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    fun convertErrorsToText(input : ErrorType):String{
        var result = ""
        if(input.identity!= null){
            result = result + input.identity +"\n"
        }
        if(input.full_name!= null){
            result = result + input.full_name +"\n"
        }
        if(input.email!= null){
            result = result + input.email +"\n"
        }
        if(input.phone!= null){
            result = result + input.phone +"\n"
        }
        if(input.password!= null){
            result = result + input.password +"\n"
        }
        if(input.identity_number!= null){
            result = result + input.identity_number +"\n"
        }
        if(input.shop_name!= null){
            result = result + input.shop_name +"\n"
        }
        if(input.shop_city_id!= null){
            result = result + input.shop_city_id +"\n"
        }
        if(input.shop_district_id!= null){
            result = result + input.shop_district_id +"\n"
        }
        if(input.shop_commune_id!= null){
            result = result + input.shop_commune_id +"\n"
        }
        if(input.business_area!= null){
            result = result + input.business_area +"\n"
        }
        if(input.group_id!= null){
            result = result + input.group_id +"\n"
        }
        if(input.merchant_id!= null){
            result = result + input.merchant_id +"\n"
        }
        if(input.reset_code!= null){
            result = result + input.reset_code +"\n"
        }
        if(input.old_password!= null){
            result = result + input.old_password +"\n"
        }
        if(input.new_password!= null){
            result = result + input.new_password +"\n"
        }
        if(input.new_password_confirm!= null){
            result = result + input.new_password_confirm +"\n"
        }
        if(input.title!= null){
            result = result + input.title +"\n"
        }
        if(input.start_time!= null){
            result = result + input.start_time +"\n"
        }
        if(input.end_time!= null){
            result = result + input.end_time +"\n"
        }
        if(input.how_to_use!= null){
            result = result + input.how_to_use +"\n"
        }
        if(input.apply_condition!= null){
            result = result + input.apply_condition +"\n"
        }
        if(input.offer_number!= null){
            result = result + input.offer_number +"\n"
        }
        if(input.type!= null){
            result = result + input.type +"\n"
        }
        if(input.status!= null){
            result = result + input.status +"\n"
        }
        if(input.discount_type!= null){
            result = result + input.discount_type +"\n"
        }
        if(input.discount_value!= null){
            result = result + input.discount_value +"\n"
        }
        if(input.discount_note!= null){
            result = result + input.discount_note +"\n"
        }
        if(input.offer_id!= null){
            result = result + input.offer_id +"\n"
        }
        if(input.dob!= null){
            result = result + input.dob +"\n"
        }
        if(input.gender!= null){
            result = result + input.gender +"\n"
        }
        if(input.fontside_image_identity_cart!= null){
            result = result + input.fontside_image_identity_cart +"\n"
        }
        if(input.backside_image_identity_cart!= null){
            result = result + input.backside_image_identity_cart +"\n"
        }
        if(input.avatar!= null){
            result = result + input.avatar +"\n"
        }
        if(input.city_id!= null){
            result = result + input.city_id +"\n"
        }
        if(input.district_id!= null){
            result = result + input.district_id +"\n"
        }
        if(input.commune_id!= null){
            result = result + input.commune_id +"\n"
        }
        if(input.address!= null){
            result = result + input.address +"\n"
        }
        if(input.shop_created!= null){
            result = result + input.shop_created +"\n"
        }
        if(input.description!= null){
            result = result + input.description +"\n"
        }
        if(input.shop_address!= null){
            result = result + input.shop_address +"\n"
        }
        if(input.merchant_registration_front_site!= null){
            result = result + input.merchant_registration_front_site +"\n"
        }
        if(input.merchant_registration_back_site!= null){
            result = result + input.merchant_registration_back_site +"\n"
        }
        if(input.bank_id!= null){
            result = result + input.bank_id +"\n"
        }
        if(input.bank_account_number!= null){
            result = result + input.bank_account_number +"\n"
        }
        return result
    }
}