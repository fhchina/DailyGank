package com.zzhoujay.dailygank.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 * Created by zhou on 16-3-9.
 */
data class Gank(@SerializedName("_id")
                var Id: String,
                @SerializedName("_ns")
                var Ns: String,
                @SerializedName("createdAt")
                var createdTime: Date,
                var desc: String,
                @SerializedName("publishedAt")
                var publishedTime: Date,
                var type: String,
                var url: String,
                var used: Boolean = false,
                var who: String? = "") : Serializable, Parcelable {
    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readSerializable() as Date, source.readString(), source.readSerializable() as Date, source.readString(), source.readString(), 1.toByte().equals(source.readByte()), source.readString())

    override fun describeContents(): Int {
        return 0
    }

    constructor() : this("", "", Date(), "", Date(), "", "", false, "")

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(Id)
        dest?.writeString(Ns)
        dest?.writeSerializable(createdTime)
        dest?.writeString(desc)
        dest?.writeSerializable(publishedTime)
        dest?.writeString(type)
        dest?.writeString(url)
        dest?.writeByte((if (used) 1 else 0).toByte())
        dest?.writeString(who)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Gank> = object : Parcelable.Creator<Gank> {
            override fun createFromParcel(source: Parcel): Gank {
                return Gank(source)
            }

            override fun newArray(size: Int): Array<Gank?> {
                return arrayOfNulls(size)
            }
        }
    }
}

data class DailyGank(val types: Array<String>, val ganks: Array<Array<Gank>>) : Serializable {

    fun size(): Int {
        return types.size
    }

    fun type(index: Int): String {
        return types[index]
    }

    fun ganks(index: Int): Array<Gank> {
        return ganks[index]
    }

    fun typeOfGanks(type: String): Gank? {
        val index = types.indexOf(type)
        if (index >= 0) {
            val g = ganks[index]
            if (g.size > 0) {
                return g[0]
            }
        }
        return null
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

data class Result(val error: Boolean, val daily: DailyGank) : Serializable


data class DateResult(val error: Boolean, val history: Array<String>) : Serializable
