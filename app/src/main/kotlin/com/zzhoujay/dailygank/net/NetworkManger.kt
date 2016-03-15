package com.zzhoujay.dailygank.net

import android.content.Context
import android.net.ConnectivityManager
import com.zzhoujay.dailygank.App
import com.zzhoujay.dailygank.R
import com.zzhoujay.dailygank.util.NetworkRequestFailureException
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.toast
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
        if (!isNetworkConnected()) {
            val msg = App.app.getString(R.string.network_error)
            App.app.toast(msg)
            throw NetworkRequestFailureException(msg)
        }
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            return response.body().string()
        } else {
            throw NetworkRequestFailureException(response.message())
        }
    }

    fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager = App.app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return ni != null && ni.isConnectedOrConnecting
    }

}

