package com.zzhoujay.dailygank.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zzhoujay.dailygank.R
import com.zzhoujay.dailygank.util.DateKit
import kotlinx.android.synthetic.main.item_data.view.*
import org.jetbrains.anko.onClick
import java.util.*

/**
 * Created by zhou on 16-3-9.
 */
class DateAdapter(context: Context, dates: Array<Date>? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var context: Context
    var dates: Array<Date>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var currSelect = 0

    var onItemCheckedListener: ((date: Date) -> Unit)? = null

    init {
        this.context = context;
        this.dates = dates;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val date = dates?.get(position)
        if (date != null && holder is Holder) {
            holder.time.text = DateKit.formatDateToDay(date)
            holder.checked.visibility = if (currSelect == position) View.VISIBLE else View.GONE
        }
    }

    override fun getItemCount(): Int {
        return dates?.size ?: 0
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): Holder? {
        val holder = Holder(LayoutInflater.from(context).inflate(R.layout.item_data, p0, false))
        holder.onCheckListener = {
            currSelect = it - 1
            notifyDataSetChanged()
            onItemCheckedListener?.invoke(dates!![it - 1])
        }
        return holder
    }

    class Holder(val root: View) : RecyclerView.ViewHolder(root) {
        val checked: ImageView
        val time: TextView

        var onCheckListener: ((i: Int) -> Unit)? = null

        init {
            checked = root.checked
            time = root.time

            root.onClick {
                onCheckListener?.invoke(adapterPosition)
            }
        }
    }
}