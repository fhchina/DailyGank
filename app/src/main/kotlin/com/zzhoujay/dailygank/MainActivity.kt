package com.zzhoujay.dailygank

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import com.bumptech.glide.Glide
import com.zzhoujay.dailygank.data.DailyProvider
import com.zzhoujay.dailygank.data.DataManager
import com.zzhoujay.dailygank.data.DateProvider
import com.zzhoujay.dailygank.model.Day
import com.zzhoujay.dailygank.ui.adapter.DailyAdapter
import com.zzhoujay.dailygank.ui.adapter.DateAdapter
import com.zzhoujay.dailygank.ui.adapter.HandlerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    companion object {
        const val fuli_name = "福利"
    }

    val dateProvider: DateProvider by lazy { DateProvider() }

    var dailyProvider: DailyProvider by Delegates.notNull<DailyProvider>()
    var handlerAdapter: HandlerAdapter by Delegates.notNull<HandlerAdapter>()
    var dailyAdapter: DailyAdapter by Delegates.notNull<DailyAdapter>()
    var dateAdapter: DateAdapter by Delegates.notNull<DateAdapter>()
    var bsb: BottomSheetBehavior<RecyclerView> by Delegates.notNull<BottomSheetBehavior<RecyclerView>>()
    var resource_ready: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.post { postOnCreate() }
    }

    fun loadDaily(day: Day) {
        val alert = ProgressDialog(this)
        alert.setMessage(getString(R.string.alert_loading))
        alert.setCancelable(false)
        alert.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        doAsync {
            val provider = DailyProvider(day)
            val data = DataManager.load(provider)
            val imageGank = data?.typeOfGanks(fuli_name)
            uiThread {
                handlerAdapter.title = day.toString()
                Glide.with(this@MainActivity).load(imageGank!!.url).into(imageView)
                dailyAdapter.daily = data
                alert.dismiss()
                resource_ready = true
            }
        }
    }

    fun refreshDate() {
        doAsync {
            val dates = DataManager.refresh(dateProvider)
            if (dates == null) {
                uiThread {
                    toast(R.string.load_failure)
                    swipeRefreshLayout.isRefreshing = false
                }
            } else {
                uiThread {
                    dateAdapter.dates = dates
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    fun loadInit(): Unit {
        val alert = ProgressDialog(this)
        alert.setMessage(getString(R.string.alert_loading))
        alert.setCancelable(false)
        alert.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        doAsync {
            val dates = DataManager.load(dateProvider)
            if (dates == null) {
                val al = alert(R.string.load_failure, R.string.title_alert) {
                    positiveButton(R.string.button_retry) {
                        loadInit()
                        dismiss()
                    }
                    negativeButton(R.string.button_exit) {
                        finish()
                    }
                }
                al.cancellable(false)
                al.show()
            } else {
                val day = dates[0]
                dailyProvider = DailyProvider(day)
                val data = DataManager.load(dailyProvider, userCache = true)
                if (data == null) {
                    toast("加载失败")
                } else {
                    val imageGank = data.typeOfGanks(fuli_name)
                    uiThread {
                        handlerAdapter.title = day.toString()
                        Glide.with(this@MainActivity).load(imageGank!!.url).into(imageView)
                        dailyAdapter.daily = data
                        dateAdapter.dates = dates
                        alert.dismiss()
                        resource_ready = true
                    }
                }
            }
        }
    }

    fun postOnCreate() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        menu_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        dailyAdapter = DailyAdapter(this)
        dateAdapter = DateAdapter(this)
        handlerAdapter = HandlerAdapter(this, dailyAdapter)

        bsb = BottomSheetBehavior.from(recyclerView)


        handlerAdapter.onHandlerClickListener = {
            if (resource_ready) {
                dailyAdapter.daily?.let {
                    when (bsb.state) {
                        BottomSheetBehavior.STATE_COLLAPSED -> bsb.state = BottomSheetBehavior.STATE_EXPANDED
                        BottomSheetBehavior.STATE_EXPANDED -> bsb.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
            }
        }


        dateAdapter.onItemCheckedListener = {
            drawer_layout.closeDrawer(Gravity.START)
            drawer_layout.postDelayed({
                loadDaily(it)
            }, 500)
        }

        recyclerView.adapter = handlerAdapter
        menu_recycler_view.adapter = dateAdapter

        swipeRefreshLayout.setColorSchemeResources(R.color.material_red_500, R.color.material_lightBlue_500,
                R.color.material_lightGreen_500, R.color.material_deepOrange_500, R.color.material_yellow_500, R.color.material_purple_500)

        swipeRefreshLayout.setOnRefreshListener { refreshDate() }

        loadInit()
    }

}
