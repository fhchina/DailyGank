package com.zzhoujay.dailygank.common

/**
 * Created by zhou on 16-3-9.
 */

object Config {

    object Url {

        const val site_url = "http://gank.io"
        const val daily_url = site_url + "/api/day/%d/%d/%d"
        const val history_url = site_url + "/api/day/history"
    }

    object Const {

        const val date_cache_file_name = "dates"
        const val min_load_time = 300

    }

}