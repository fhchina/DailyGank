package com.zzhoujay.dailygank.data

import android.util.Log

/**
 * Created by zhou on 16-3-10.
 */
object DataManager {

    fun <T> load(provider: Provider<T>, fromNetwork: Boolean = false): T? {
        var r: T? = null
        if (fromNetwork) {
            r = try {
                provider.loadFromNetwork()
            } catch(e: Exception) {
                Log.i("DataManager", "load", e)
                null
            }
        }
        if (r == null) {
            r = provider.persistence().load(provider.key())
        }
        provider.set(r)
        if (fromNetwork && r != null) {
            provider.store(provider.key(), provider.get())
        }
        return provider.get()
    }

}

