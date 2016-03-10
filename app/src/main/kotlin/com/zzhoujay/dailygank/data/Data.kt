package com.zzhoujay.dailygank.data

import com.zzhoujay.dailygank.common.Config
import com.zzhoujay.dailygank.model.DailyGank
import com.zzhoujay.dailygank.net.NetworkManger
import com.zzhoujay.dailygank.persistence.DailyPersistence
import com.zzhoujay.dailygank.persistence.Persistence
import com.zzhoujay.dailygank.util.JsonKit
import okhttp3.Request
import java.util.*

/**
 * Created by zhou on 16-3-9.
 */

interface Provider<T> {

    fun loadFromNetwork(): T

    fun persistence(): Persistence<String, T>

    fun get(): T

}

class DailyProvider(val day: Date) : Provider<DailyGank> {

    val calendar: Calendar

    init {
        calendar = Calendar.getInstance()
        calendar.time = day
    }

    val persistence: DailyPersistence by lazy { DailyPersistence() }

    override fun persistence(): Persistence<String, DailyGank> {
        return persistence
    }


    override fun loadFromNetwork(): DailyGank {
        val request = Request.Builder()
                .get()
                .url(String.format(Config.Url.daily_url, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)))
                .build()
        val result = JsonKit.generate(NetworkManger.requestStringSync(request))
        return result.daily
    }


    override fun get(): DailyGank {
        throw UnsupportedOperationException()
    }

}