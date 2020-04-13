package com.example.shop49k.utils.`object`

import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

object DateUtil {
    object DateDifferenceExample {
        fun getTimeBetween(dateCheck: String) {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            val date1 = formatter.parse(dateCheck)
            // Creates two calendars instances
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()

            // Set the date for both of the calendar instance
            cal1.set(2006, Calendar.DECEMBER, 30)
            cal2.set(2007, Calendar.MAY, 3)

            // Get the represented date in milliseconds
            val millis1 = cal1.getTimeInMillis()
            val millis2 = cal2.getTimeInMillis()

            // Calculate difference in milliseconds
            val diff = millis2 - millis1

            // Calculate difference in seconds
            val diffSeconds = diff / 1000

            // Calculate difference in minutes
            val diffMinutes = diff / (60 * 1000)

            // Calculate difference in hours
            val diffHours = diff / (60 * 60 * 1000)

            // Calculate difference in days
            val diffDays = diff / (24 * 60 * 60 * 1000)

            println("In milliseconds: $diff milliseconds.")
            println("In seconds: $diffSeconds seconds.")
            println("In minutes: $diffMinutes minutes.")
            println("In hours: $diffHours hours.")
            println("In days: $diffDays days.")
        }
    }

    fun convertDateToStringOrgManagement(dateCheck:String): String {
        if(dateCheck.isNotEmpty()){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            var date = dateFormat.parse(dateCheck)
            val dateFormatTimeResult = SimpleDateFormat("hh:mm - dd/MM/yyyy",
                Locale.getDefault())
            return dateFormatTimeResult.format(date).toString()
        }else{
            return ""
        }
    }

    fun convertDateToStringHasHour(dateCheck:String): String {
        if(dateCheck.isNotEmpty()){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            var date = dateFormat.parse(dateCheck)
            val dateFormatTimeResult = SimpleDateFormat("HH:mm - dd/MM/yyyy",
                Locale.getDefault())
            return dateFormatTimeResult.format(date).toString()
        }else{
            return ""
        }
    }

    fun convertDateToStringFirstSale(dateCheck:String): String {
        if(dateCheck.isNotEmpty()){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            var date = dateFormat.parse(dateCheck)
            val dateFormatTimeResult = SimpleDateFormat("dd-MM",
                Locale.getDefault())
            return dateFormatTimeResult.format(date).toString().replace("-","\n")
        }else{
            return ""
        }
    }

    fun convertDateToStringBirthDay(dateCheck:String): String {
        if(dateCheck.isNotEmpty()){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            var date = dateFormat.parse(dateCheck)
            val dateFormatTimeResult = SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault())
            return dateFormatTimeResult.format(date).toString()
        }else{
            return ""
        }

    }

    fun convertDateToStringBirthDayInput(dateCheck:String): String {
        if(dateCheck.isNotEmpty()){
            val dateFormat = SimpleDateFormat("MM-dd-yyyy",
                Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            var date = dateFormat.parse(dateCheck)
            val dateFormatTimeResult = SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault())
            return dateFormatTimeResult.format(date).toString()
        }else{
            return ""
        }
    }

}