package com.zzhoujay.dailygank.ui.view

import android.os.Parcel
import android.text.TextPaint
import android.text.style.URLSpan

/**
 * Created by zhou on 16-3-11.
 */
class URLSpanNoUnderline : URLSpan {
    constructor(url: String) : super(url) {
    }

    @SuppressWarnings("unused")
    constructor(src: Parcel) : super(src) {
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
    }

}