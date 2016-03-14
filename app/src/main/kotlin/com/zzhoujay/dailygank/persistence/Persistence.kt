package com.zzhoujay.dailygank.persistence

import com.zzhoujay.dailygank.App
import com.zzhoujay.dailygank.model.DailyGank
import com.zzhoujay.dailygank.util.IOKit
import java.io.File
import java.io.Serializable
import java.util.*

/**
 * Created by zhou on 16-3-9.
 */
interface Persistence<K, V> {

    fun load(k: K): V?

    fun store(k: K, v: V?)

    fun lastModifyTime(k: K): Date?
}

open class ObjectPersistence<T : Serializable> : Persistence<String, T> {

    override fun lastModifyTime(k: String): Date? {
        return IOKit.getFileLastModifyTime(File(App.app.cacheDir, "$k.cache"))
    }

    override fun load(k: String): T? {
        val t = IOKit.readObjectFromFile(File(App.app.cacheDir, "$k.cache"))
        return try {
            t as T
        } catch(e: Exception) {
            null
        }
    }

    override fun store(k: String, v: T?) {
        if (v != null)
            IOKit.writeObjectToFile(File(App.app.cacheDir, "$k.cache"), v)
    }
}

class DailyPersistence : ObjectPersistence<DailyGank>()

class DatePersistence : ObjectPersistence<Array<Date>>()
