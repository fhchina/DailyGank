package com.zzhoujay.dailygank.data

import com.zzhoujay.dailygank.common.Config
import com.zzhoujay.dailygank.model.DailyGank
import com.zzhoujay.dailygank.model.DateResult
import com.zzhoujay.dailygank.net.NetworkManger
import com.zzhoujay.dailygank.persistence.DailyPersistence
import com.zzhoujay.dailygank.persistence.ObjectPersistence
import com.zzhoujay.dailygank.persistence.Persistence
import com.zzhoujay.dailygank.util.DateKit
import com.zzhoujay.dailygank.util.HashKit
import com.zzhoujay.dailygank.util.JsonKit
import okhttp3.Request
import java.io.Serializable
import java.util.*

/**
 * Created by zhou on 16-3-9.
 * 数据提供器的抽象接口
 */
interface Provider<T> : Persistence<String, T> {

    fun loadFromNetwork(): T

    fun get(): T?

    fun set(t: T?)

    fun key(): String
}

/**
 * 实现了Persistence接口的Provider
 */
abstract class PersistenceProvider<T> : Provider<T> {

    var value: T? = null

    /**
     * 获取persistence实例
     */
    abstract fun persistence(): Persistence<String, T>

    override fun set(t: T?) {
        this.value = t
    }

    override fun get(): T? {
        return value
    }

    override fun store(k: String, v: T?) {
        persistence().store(k, v)
    }

    override fun load(k: String): T? {
        return persistence().load(k)
    }

}

abstract class UniversalProvider<T : Serializable>(val key: String) : PersistenceProvider<T>() {

    override fun key(): String {
        return key
    }

    private val persistence: ObjectPersistence<T> by lazy { ObjectPersistence<T>() }

    override fun persistence(): Persistence<String, T> {
        return persistence
    }

}


class DailyProvider(val day: Calendar) : UniversalProvider<DailyGank>(HashKit.md5("year:${day.get(Calendar.YEAR)}-month:${day.get(Calendar.MONTH)}-day:${day.get(Calendar.DAY_OF_MONTH)}")) {

    override fun loadFromNetwork(): DailyGank {
        val request = Request.Builder()
                .get()
                .url(String.format(Config.Url.daily_url, day.get(Calendar.YEAR), day.get(Calendar.MONTH) + 1, day.get(Calendar.DAY_OF_MONTH)))
                .build()
        val result = JsonKit.generate(NetworkManger.requestStringSync(request))
        if (result.error) {
            throw RuntimeException("request data failure")
        }
        return result.daily
    }

}

class DateProvider : UniversalProvider<Array<Date>>(Config.Const.date_cache_file_name) {
    override fun loadFromNetwork(): Array<Date> {
        val request = Request.Builder()
                .get()
                .url(Config.Url.history_url)
                .build()
        val result = JsonKit.generateDate(NetworkManger.requestStringSync(request))
        if (result.error) {
            throw RuntimeException("request data failure")
        }
        return result.history
    }
}
