package com.zzhoujay.dailygank.data

import android.util.Log
import com.zzhoujay.dailygank.util.DateKit
import java.util.*

/**
 * Created by zhou on 16-3-10.
 */
object DataManager {

    const val NONE_UPDATE = -1
    const val ONE_DAY = 1
    const val TWO_DAY = 2
    const val THREE_DAY = 3
    const val ONE_WEEK = 7

    fun <T> refresh(provider: Provider<T>): T? {
        return load(provider, fromNetwork = true, userCache = false)
    }

    /**
     * 通过Provider自动选择从网络、缓存加载数据
     */
    fun <T> load(provider: Provider<T>, fromNetwork: Boolean = false, userCache: Boolean = true, updateTime: Int = NONE_UPDATE): T? {
        var r: T? = null
        var flag: Boolean = false
        fun <T> loadFromNetwork(provider: Provider<T>): T? {
            return try {
                flag = true
                provider.loadFromNetwork()
            } catch(e: Exception) {
                Log.i("DataManager", "load", e)
                flag = false
                null
            }
        }

        var needToUpdate: Boolean = false
        if (updateTime > 0) {
            val lastC = provider.lastModifyTime(provider.key())
            if (lastC != null) {
                val dd = DateKit.compareDay(System.currentTimeMillis(), lastC)
                needToUpdate = dd >= updateTime
            }
        }
        if (needToUpdate || fromNetwork) {
            r = loadFromNetwork(provider)
        }
        if (userCache && r == null) {
            r = provider.load(provider.key())
        }
        if (!(needToUpdate || fromNetwork) && r == null) {
            r = loadFromNetwork(provider)
        }
        provider.set(r)
        if (flag && r != null) {
            if (provider.needStore(r))
                provider.store(provider.key(), provider.get())
        }
        return provider.get()
    }

}

