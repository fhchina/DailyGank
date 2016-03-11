package com.zzhoujay.dailygank

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.zzhoujay.dailygank.data.DailyProvider
import com.zzhoujay.dailygank.data.DataManager
import com.zzhoujay.dailygank.ui.adapter.DailyAdapter
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
        recyclerView.adapter = dailyAdapter

//        Glide.with(this).load("http://ww4.sinaimg.cn/large/7a8aed7bjw1f1bdal8i3nj20f00lf77g.jpg").into(image)
        async() {
            val c = Calendar.getInstance()
            val provider = DailyProvider(c.time)
            val r = DataManager.load(provider, true)
            val g = r?.typeOfGanks("福利")
            uiThread {
                dailyAdapter.daily = r
                if (g != null) {
                    Glide.with(this@MainActivity).load(g.url).into(image)
                }
            }
        }

    }


}
