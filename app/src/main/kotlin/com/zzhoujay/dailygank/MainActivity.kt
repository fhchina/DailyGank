package com.zzhoujay.dailygank

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.zzhoujay.dailygank.view.adapter.GankAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = GankAdapter(this, arrayListOf("", "", "", "", "", "", "", ""))

        Glide.with(this).load("http://ww4.sinaimg.cn/large/7a8aed7bjw1f1bdal8i3nj20f00lf77g.jpg").into(image)

    }


}
