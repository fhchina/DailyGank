package com.zzhoujay.dailygank.persistence

/**
 * Created by zhou on 16-3-9.
 */

interface CachePool<V> {

    fun size(): Int

    fun put(key: String, value: V)

    fun get(key: String): V

}

class GankCachePool