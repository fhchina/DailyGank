package com.zzhoujay.dailygank.util

/**
 * Created by zhou on 16-3-13.
 */


interface TaskQueue {
    fun addTask(t: () -> Unit)
}