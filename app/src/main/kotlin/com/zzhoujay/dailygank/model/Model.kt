package com.zzhoujay.dailygank.model

import com.alibaba.fastjson.annotation.JSONField
import java.io.Serializable

import java.util.Date

/**
 * Created by zhou on 16-3-9.
 */
data class Gank(@JSONField(name = "_id")
                var Id: String,
                @JSONField(name = "_ns")
                var Ns: String,
                @JSONField(name = "createdAt", format = "yyyy-MM-dd'T'HH:mm:ss.SSS")
                var createdTime: Date,
                var desc: String,
                @JSONField(name = "publishedAt", format = "yyyy-MM-dd'T'HH:mm:ss.SSS")
                var publishedTime: Date,
                var type: String,
                var url: String,
                var used: Boolean = false,
                var who: String) : Serializable

data class DailyGank(val types: Array<String>, val ganks: Array<Array<Gank>>) : Serializable

data class Result(val error: Boolean, val daily: DailyGank) : Serializable