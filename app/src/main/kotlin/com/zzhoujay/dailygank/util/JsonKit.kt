package com.zzhoujay.dailygank.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.zzhoujay.dailygank.model.DailyGank
import com.zzhoujay.dailygank.model.DateResult
import com.zzhoujay.dailygank.model.Gank
import com.zzhoujay.dailygank.model.Result
import java.util.*

/**
 * Created by zhou on 16-3-9.
 */
object JsonKit {

    val gson: Gson by lazy {
        GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create()
    }

    fun generate(json: String): Result {
        val parser = JsonParser()
        val element = parser.parse(json)
        val root = element.asJsonObject
        val category = root.getAsJsonArray("category")
        val iterator = category.iterator()
        val results = root.getAsJsonObject("results")
        val error = root.get("error").asBoolean
        if (!error) {
            val types = ArrayList<String>(category.size())
            val ghs = ArrayList<Array<Gank>>(category.size())
            while (iterator.hasNext()) {
                val type = iterator.next()
                val t = type.asString
                types.add(t)
                val e = results.get(t).toString()
                val gs = gson.fromJson<Array<Gank>>(e, object : TypeToken<Array<Gank>>() {}.type)
                ghs.add(gs)
            }
            return Result(error, DailyGank(types.toTypedArray(), ghs.toTypedArray()))
        } else {
            return Result(error, DailyGank(emptyArray(), emptyArray()))
        }
    }

    fun generateDate(json: String): DateResult {
        val parser = JsonParser()
        val element = parser.parse(json)
        val root = element.asJsonObject
        val error = root.get("error").asBoolean
        val results = root.getAsJsonArray("results")
        if (!error) {
            val r = results.map { DateKit.parserDateFromDay(it.asString) }
            return DateResult(error, r.toTypedArray())
        }
        return DateResult(error, emptyArray())
    }
}
