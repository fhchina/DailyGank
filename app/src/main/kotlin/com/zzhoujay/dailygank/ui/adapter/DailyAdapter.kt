package com.zzhoujay.dailygank.ui.adapter

import android.content.Context
import android.content.res.Resources
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zzhoujay.dailygank.R
import com.zzhoujay.dailygank.model.DailyGank
import com.zzhoujay.dailygank.util.TextKit
import kotlinx.android.synthetic.main.item_daily.view.*

/**
 * Created by zhou on 16-3-9.
 */
class DailyAdapter(val context: Context, dailyGank: DailyGank? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var daily: DailyGank? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        this.daily = dailyGank
    }

    override fun getItemCount(): Int {
        return daily?.size() ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, type: Int): Holder? {
        val holder: Holder;
        holder = Holder(LayoutInflater.from(context).inflate(R.layout.item_daily, parent, false))
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is Holder) {
            holder.title.text = daily!!.type(position)
            holder.content.text = TextKit.generate(daily!!.ganks(position), ContextCompat.getColor(context, R.color.material_lightBlue_500))
        }
    }

    class Holder(val root: View) : RecyclerView.ViewHolder(root) {

        val title: TextView
        val content: TextView

        init {
            title = root.title
            content = root.content
            content.movementMethod = LinkMovementMethod.getInstance()
        }
    }

}