package com.zzhoujay.dailygank.data

import android.util.Log

/**
 * Created by zhou on 16-3-10.
 */
object DataManager {

    /**
     * 通过Provider自动选择从网络、缓存加载数据
     */
    fun <T> load(provider: Provider<T>, fromNetwork: Boolean = false, userCache: Boolean = true): T? {
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
        if (fromNetwork) {
            r = loadFromNetwork(provider)
        }
        if (userCache && r == null) {
            r = provider.persistence().load(provider.key())
        }
        if (!fromNetwork && r == null) {
            r = loadFromNetwork(provider)
        }
        provider.set(r)
        if (flag && r != null) {
            provider.store(provider.key(), provider.get())
        }
        return provider.get()
    }

}

