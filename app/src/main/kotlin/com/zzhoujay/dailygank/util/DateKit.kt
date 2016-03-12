package com.zzhoujay.dailygank.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zhou on 16-3-9.
 */

object DateKit {

    val dayFormat: SimpleDateFormat by lazy { SimpleDateFormat("yyyy-MM-dd") }

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
}

