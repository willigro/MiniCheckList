package com.rittamann.minichecklist.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private const val DATE_REPRESENTATIVE = "dd 'de' MMMM',' yyyy"

    fun dbFormat(calendar: Calendar): String {
        val format = getFormat()
        format.timeZone = calendar.timeZone
        val time = calendar.time
        return format.format(time)
    }

    fun parseDate(string: String): Calendar {
        val calendar = Calendar.getInstance()
        try {
            calendar.time = getFormat().parse(string)!!
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return calendar
    }

    @SuppressLint("SimpleDateFormat")
    fun parseDateRepresentative(date: String): Calendar {
        val calendar = Calendar.getInstance()
        try {
            calendar.time = SimpleDateFormat(DATE_REPRESENTATIVE).parse(date)!!
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return calendar
    }

    @SuppressLint("SimpleDateFormat")
    fun parseDateRepresentative(calendar: Calendar): String {
        try {
            return SimpleDateFormat(DATE_REPRESENTATIVE).format(calendar.time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    }
}