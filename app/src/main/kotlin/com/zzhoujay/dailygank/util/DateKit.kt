package com.zzhoujay.dailygank.util

import com.zzhoujay.dailygank.model.Day
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

    fun compareToday(day: Day): Int {
        val c = Calendar.getInstance()
        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH) + 1
        val d = c.get(Calendar.DAY_OF_MONTH)
        val r: Int
        r = if (y > day.year) {
            -1
        } else if (y < day.year) {
            1
        } else {
            if (m > day.month) {
                -1
            } else if (m < day.month) {
                1
            } else {
                day.day - d
            }
        }
        return r
    }

    fun compareDay(d1: Long, d2: Long): Int {
        val c1 = Calendar.getInstance()
        c1.timeInMillis = d1
        val c2 = Calendar.getInstance()
        c2.timeInMillis = d2
        val dd = (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 365 +
                (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH)) * 30 +
                (c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH))
        return dd
    }

    fun friendlyFormatDate(date: Date): String {
        return friendlyDayFormat.format(date)
    }
}

