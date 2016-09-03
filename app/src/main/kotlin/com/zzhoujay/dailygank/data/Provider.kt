package com.zzhoujay.dailygank.data

import com.zzhoujay.dailygank.common.Config
import com.zzhoujay.dailygank.model.DailyGank
import com.zzhoujay.dailygank.model.Day
import com.zzhoujay.dailygank.net.NetworkManger
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

    fun needStore(t: T): Boolean
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

    override fun lastModifyTime(k: String): Long? {
        return persistence().lastModifyTime(k)
    }

    override fun needStore(t: T): Boolean {
        return true
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


class DailyProvider(val day: Day) : UniversalProvider<DailyGank>(HashKit.md5("year:${day.year}-month:${day.month}-day:${day.day}")) {

    override fun loadFromNetwork(): DailyGank {
        val request = Request.Builder()
                .get()
                .url(String.format(Config.Url.daily_url, day.year, day.month, day.day))
                .build()
        val result = JsonKit.generate(NetworkManger.requestStringSync(request))
        if (result.error) {
            throw RuntimeException("request data failure")
        }
        return result.daily
    }

    override fun needStore(t: DailyGank): Boolean {
        return (!t.ganks.isEmpty() && !t.types.isEmpty())
    }

}

class DateProvider : UniversalProvider<Array<Day>>(Config.Const.date_cache_file_name) {
    override fun loadFromNetwork(): Array<Day> {
        val request = Request.Builder()
                .get()
                .url(Config.Url.history_url)
                .build()
        val result = JsonKit.generateDate(NetworkManger.requestStringSync(request))
        if (result.error) {
            throw RuntimeException("request data failure")
        }
        val list = result.history.map { Day.fromString(it) }
        return list.toTypedArray()
    }

    override fun needStore(t: Array<Day>): Boolean {
        if (t.size > 0) {
            return DateKit.compareToday(t[0]) >= 0
//            val dd = DateKit.compareDay(System.currentTimeMillis(), t[0].time)
//            return dd <= 0
        }
        return false
    }
}
