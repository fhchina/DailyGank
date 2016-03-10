package com.zzhoujay.dailygank.util

import android.util.Log
import java.io.*

/**
 * Created by zhou on 16-3-9.
 */
object IOKit{

    fun writeObjectToFile(file: File, obj: Serializable) {
        if (file.exists()) {
            var oos: ObjectOutputStream? = null
            try {
                oos = ObjectOutputStream(FileOutputStream(file))
                oos.writeObject(obj)
                oos.flush()
            } catch(e: Exception) {
                Log.i("writeObjectToFile", "error", e)
            } finally {
                oos?.close()
            }
        }
    }

    fun readObjectFromFile(file: File): Serializable? {
        if (file.exists()) {
            var ois: ObjectInputStream? = null
            try {
                ois = ObjectInputStream(FileInputStream(file))
                return ois.readObject() as Serializable
            } catch(e: Exception) {
                Log.i("writeObjectToFile", "error", e)
            } finally {
                ois?.close()
            }
        }
        return null
    }
}

