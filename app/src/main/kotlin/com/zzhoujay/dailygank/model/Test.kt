package com.zzhoujay.dailygank.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by zhou on 16-3-13.
 */
object Test {

    fun gg() {
        val gson = Gson()
        val gs = gson.fromJson<Array<Gank>>("", object : TypeToken<Array<Gank>>() {

        }.type)
    }
}
