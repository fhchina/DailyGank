package com.zzhoujay.dailygank.model

/**
 * Created by zhou on 16-9-3.
 */
data class Day(val year: Int, val month: Int, val day: Int) {
    companion object {
        fun fromString(str: String): Day {
            val ss = str.split('-')
            var y: Int = 0
            var m: Int = 0
            var d: Int = 0
            ss.forEachIndexed { i, s ->
                if (i == 0) {
                    y = s.toInt()
                } else if (i == 1) {
                    m = s.toInt()
                } else if (i == 2) {
                    d = s.toInt()
                } else {
                    return@forEachIndexed
                }
            }
            return Day(y, m, d)
        }
    }

    override fun equals(other: Any?): Boolean =
            if (other != null && other is Day) {
                year == other.year && month == other.month && day == other.day
            } else {
                false
            }


    override fun toString(): String {
        return "${year}年${month}月${day}日"
    }


}