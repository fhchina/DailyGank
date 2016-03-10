package com.zzhoujay.dailygank.util

import com.alibaba.fastjson.JSON
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.zzhoujay.dailygank.model.DailyGank
import com.zzhoujay.dailygank.model.Gank
import com.zzhoujay.dailygank.model.Result

import java.util.ArrayList

/**
 * Created by zhou on 16-3-9.
 */
object JsonKit {
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
                val gs = JSON.parseArray(e, Gank::class.java)
                ghs.add(gs.toTypedArray())
            }
            return Result(error, DailyGank(types.toTypedArray(), ghs.toTypedArray()))
        } else {
            return Result(error, DailyGank(emptyArray(), emptyArray()))
        }
    }
}
