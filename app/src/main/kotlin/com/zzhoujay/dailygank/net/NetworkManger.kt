package com.zzhoujay.dailygank.net

import com.zzhoujay.dailygank.App
import com.zzhoujay.dailygank.util.NetworkRequestFailureException
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * Created by zhou on 16-3-9.
 */

object NetworkManger {

    private val client: OkHttpClient by lazy {
        val cache = Cache(App.app.cacheDir, 1024 * 1024 * 10)
        OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(5, TimeUnit.SECONDS)
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

}

