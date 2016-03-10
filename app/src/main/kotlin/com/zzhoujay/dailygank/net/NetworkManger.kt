package com.zzhoujay.dailygank.net

import com.alibaba.fastjson.JSON
import com.google.gson.reflect.TypeToken
import com.zzhoujay.dailygank.App
import com.zzhoujay.dailygank.util.NetworkRequestFailureException
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type

/**
 * Created by zhou on 16-3-9.
 */

object NetworkManger {

    val client: OkHttpClient by lazy {
        val cache = Cache(App.app.cacheDir, 1024 * 1024 * 10)
        OkHttpClient.Builder()
                .cache(cache)
                .build()
    }

    fun requestStringSync(request: Request): String {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            return response.body().string()
        } else {
            throw NetworkRequestFailureException(response.message())
        }
    }

    fun <T> requestObjectSync(request: Request): T {
        val result = requestStringSync(request)
        val obj = JSON.parse(result)

    }

}

