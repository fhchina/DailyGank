package com.zzhoujay.dailygank.ui.adapter

import android.content.Context
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
import kotlinx.android.synthetic.main.item_handler.view.*

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
        return (daily?.size() ?: 0) + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, type: Int): RecyclerView.ViewHolder? {
        val holder: RecyclerView.ViewHolder;
        if (type == type_top) {
            holder = HandlerHolder(LayoutInflater.from(context).inflate(R.layout.item_handler, parent, false))
        } else {
            holder = Holder(LayoutInflater.from(context).inflate(R.layout.item_daily, parent, false))
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is Holder) {
            holder.title.text = daily!!.type(position - 1)
            holder.content.text = TextKit.generate(daily!!.ganks(position - 1), context.getColor(R.color.material_lightBlue_500))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return type_top
        } else {
            return type_item
        }
    }


    class Holder(val root: View) : RecyclerView.ViewHolder(root) {

        val title: TextView
        val content: TextView

        init {
            title = root.title
            content = root.content
            title.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    class HandlerHolder(val root: View) : RecyclerView.ViewHolder(root) {

        val title: TextView

        init {
            title = root.handler_title
        }
    }

    companion object {
        const val type_top = -1
        const val type_item = 0
    }
}