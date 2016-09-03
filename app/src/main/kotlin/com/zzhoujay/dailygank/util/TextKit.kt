package com.zzhoujay.dailygank.util

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

import com.zzhoujay.dailygank.model.Gank
import com.zzhoujay.dailygank.ui.view.URLSpanNoUnderline

/**
 * Created by zhou on 16-3-11.
 */
object TextKit {

    fun generate(ganks: Array<Gank>, color: Int): SpannableStringBuilder {
        val builder = SpannableStringBuilder()
        var start: Int
        for (gh in ganks) {
            start = builder.length
            builder.append(" •  ")
            builder.setSpan(StyleSpan(Typeface.BOLD), start, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            start = builder.length
            builder.append(gh.desc)
            builder.setSpan(URLSpanNoUnderline(gh.url), start, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(ForegroundColorSpan(color), start, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.append("（")
            builder.append(gh.who?:"null")
            builder.append("）\n")
        }
        return builder
    }
}
