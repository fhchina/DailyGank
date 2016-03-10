package com.zzhoujay.dailygank

import android.app.Application
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-9.
 */
class App : Application() {

    companion object {
        var app: App by Delegates.notNull<App>()
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }



}