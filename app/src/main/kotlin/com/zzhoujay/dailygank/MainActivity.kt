package com.zzhoujay.dailygank

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.zzhoujay.dailygank.data.DailyProvider
import com.zzhoujay.dailygank.data.DataManager
import com.zzhoujay.dailygank.data.DateProvider
import com.zzhoujay.dailygank.ui.adapter.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val dailyAdapter = DailyAdapter(this)
        val loadingAdapter = LoadingAdapter(this)
        val dateAdapter = DateAdapter(this)
        val statusAdapter = StatusAdapter(Status.loading.name to loadingAdapter,
                Status.normal.name to dailyAdapter,
                Status.date.name to dateAdapter)
        val handlerAdapter = HandlerAdapter(this, statusAdapter)

        recyclerView.adapter = handlerAdapter
        //
        //        async() {
        ////            val c = Calendar.getInstance()
        ////            c.set(Calendar.DAY_OF_MONTH, 11)
        ////            val provider = DailyProvider(c.time)
        ////            val r = DataManager.load(provider)
        ////            val g = r?.typeOfGanks("福利")
        //
        //            val p1=DateProvider()
        //            val r1=DataManager.load(p1)
        //            uiThread {
        //                dateAdapter.dates=r1
        //                statusAdapter.switch(Status.date.name)
        ////                dailyAdapter.daily = r
        ////                if (g != null) {
        ////                    Glide.with(this@MainActivity).load(g.url).into(image)
        ////                }
        //            }
        //        }
        async() {
            val provider = DateProvider()
            val r = DataManager.load(provider)
            uiThread {
                dateAdapter.dates = r
                statusAdapter.switch(Status.date.name)
            }
        }

    }


}
