package com.zzhoujay.dailygank.data

/**
 * Created by zhou on 16-3-10.
 */
object DataManager {

    fun <T> load(provider: Provider<T>): T {
        val r = provider.get()
        if(r!=null){
            provider.set(r)
        }
    }

}

