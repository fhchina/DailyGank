package com.zzhoujay.dailygank.ui.adapter

import android.content.Context
import android.graphics.Color
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zzhoujay.dailygank.R
import com.zzhoujay.dailygank.model.Day
import kotlinx.android.synthetic.main.item_data.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.onClick

/**
 * Created by zhou on 16-3-9.
 */
class DateAdapter(context: Context, dates: Array<Day>? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var context: Context
    var dates: Array<Day>? = null
        set(value) {
            field = value
            if (currSelect == null && value != null) {
                currSelect = value[0]
                lastPosition = 0
            }
            notifyDataSetChanged()
        }
    private var currSelect: Day? = null
    private var lastPosition: Int = 0

    var onItemCheckedListener: ((day: Day) -> Unit)? = null

    init {
        this.context = context
        this.dates = dates
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val day = dates?.get(position)
        if (day != null && holder is Holder) {
            holder.time.text = day.toString()
            holder.root.backgroundColor = if (day.equals(currSelect)) Color.LTGRAY else Color.WHITE
        }
    }

    override fun getItemCount(): Int {
        return dates?.size ?: 0
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): Holder? {
        val holder = Holder(LayoutInflater.from(context).inflate(R.layout.item_data, p0, false))
        holder.onCheckListener = {
            onItemCheckedListener?.invoke(dates!![it])
            currSelect = dates!![it]
            notifyItemChanged(lastPosition)
            notifyItemChanged(it)
            lastPosition = it
        }
        return holder
    }

    class Holder(val root: View) : RecyclerView.ViewHolder(root) {
        val time: TextView

        var onCheckListener: ((i: Int) -> Unit)? = null

        init {
            time = root.time

            root.onClick {
                onCheckListener?.invoke(adapterPosition)
            }
        }
    }
}