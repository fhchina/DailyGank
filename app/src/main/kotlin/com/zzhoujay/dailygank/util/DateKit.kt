package com.zzhoujay.dailygank.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zhou on 16-3-9.
 */

object DateKit {

    val dayFormat: SimpleDateFormat by lazy { SimpleDateFormat("yyyy-MM-dd") }
    val friendlyDayFormat: SimpleDateFormat by lazy { SimpleDateFormat("yyyy年MM月dd日") }
    val one_day_time: Int by lazy { 1000 * 24 * 3600 }

    fun formatDateToDay(date: Date): String {
        return dayFormat.format(date)
    }

    fun parserDateFromDay(str: String): Date {
        return try {
            dayFormat.parse(str)
        } catch(e: Exception) {
            Date()
        }
    }

    fun compareDay(d1: Date, d2: Date): Int {
        return ((d1.time - d2.time) / one_day_time).toInt()
    }

    fun friendlyFormatDate(date: Date): String {
        return friendlyDayFormat.format(date)
    }
}

