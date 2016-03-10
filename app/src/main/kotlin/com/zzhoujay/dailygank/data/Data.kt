package com.zzhoujay.dailygank.data

import com.zzhoujay.dailygank.common.Config
import com.zzhoujay.dailygank.model.DailyGank
import com.zzhoujay.dailygank.model.Gank
import com.zzhoujay.dailygank.net.NetworkManger
import com.zzhoujay.dailygank.persistence.DailyPersistence
import com.zzhoujay.dailygank.persistence.Persistence
import com.zzhoujay.dailygank.util.DateKit
import com.zzhoujay.dailygank.util.HashKit
import com.zzhoujay.dailygank.util.JsonKit
import com.zzhoujay.dailygank.util.NetworkRequestFailureException
import okhttp3.Request
import java.util.*

/**
 * Created by zhou on 16-3-9.
 */

interface Provider<T> : Persistence<String, T> {

    fun loadFromNetwork(): T

    fun persistence(): Persistence<String, T>

    fun get(): T?

    fun set(t: T?)

    fun key(): String
}


class DailyProvider(val day: Date) : Provider<DailyGank> {

    val calendar: Calendar
    var daily: DailyGank? = null
    val key: String

    init {
        calendar = Calendar.getInstance()
        calendar.time = day
        key = HashKit.md5(DateKit.formatDateToDay(day))
    }

    override fun load(k: String): DailyGank? {
        return persistence.load(k)
    }

    override fun store(k: String, v: DailyGank?) {
        persistence.store(k, v)
    }

    override fun set(t: DailyGank?) {
        this.daily = t
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
        if (result.error) {
            throw RuntimeException("request data failure")
        }
        return result.daily
    }


    override fun get(): DailyGank? {
        return daily
    }

    override fun key(): String {
        return key
    }

}
