package com.zzhoujay.dailygank.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.zzhoujay.dailygank.R
import kotlinx.android.synthetic.main.item_pager.view.*
import java.util.*

/**
 * Created by zhou on 16-3-9.
 */
class DailyPagerAdapter(context: Context? = null, dates: ArrayList<Date>? = null) : RecyclerView.Adapter<DailyPagerAdapter.Holder>() {

    private var context: Context?;
    private var dates: ArrayList<Date>?;

    init {
        this.context = context;
        this.dates = dates;
    }


    override fun onBindViewHolder(p0: Holder?, p1: Int) {
    }

    override fun getItemCount(): Int {
        return dates?.size ?: 0
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): Holder? {
        if (context == null) {
            context = p0?.context;
        }
        val holder = Holder(LayoutInflater.from(context).inflate(R.layout.item_pager, null))
        return holder
    }

    class Holder(val root: View) : RecyclerView.ViewHolder(root) {
        val image: ImageView
//        val recyclerView: RecyclerView

        init {
            image = root.image
//            recyclerView = root.recyclerView
        }
    }
}