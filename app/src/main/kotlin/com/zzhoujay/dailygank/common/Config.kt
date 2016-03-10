package com.zzhoujay.dailygank.common

/**
 * Created by zhou on 16-3-9.
 */

object Config {

    object Database {

        const val version = 1

        const val database_name = "ganks.db"
        const val table_gank_name = "ganks"
        const val table_time_name = "times"
        const val column_id = "id"

    }

    object Url {

        const val site_url = "http://gank.io"
        const val daily_url = site_url + "/api/day/%d/%d/%d"
        const val history_url = site_url + "/api/day/history"
    }

}