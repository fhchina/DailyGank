package com.zzhoujay.dailygank.persistence

import com.zzhoujay.dailygank.App
import com.zzhoujay.dailygank.model.DailyGank
import com.zzhoujay.dailygank.util.IOKit
import java.io.File

/**
 * Created by zhou on 16-3-9.
 */
interface Persistence<K, V> {

    fun load(k: K): V?

    fun store(k: K, v: V)

}

class DailyPersistence : Persistence<String, DailyGank> {
    override fun load(k: String): DailyGank? {
        val daily = IOKit.readObjectFromFile(File(App.app.cacheDir, "$k.cache}"))
        if (daily != null && daily is DailyGank) {
            return daily
        }
        return null
    }

    override fun store(k: String, v: DailyGank) {
        IOKit.writeObjectToFile(File(App.app.cacheDir, "$k.cache"), v)
    }

}

