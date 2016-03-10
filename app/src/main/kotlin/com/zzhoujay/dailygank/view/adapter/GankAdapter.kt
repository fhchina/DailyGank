package com.zzhoujay.dailygank.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zzhoujay.dailygank.R
import kotlinx.android.synthetic.main.item_daily.view.*
import kotlinx.android.synthetic.main.item_handler.view.*

/**
 * Created by zhou on 16-3-9.
 */
class GankAdapter(val context: Context, ganks: List<String>? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var ganks: List<String>?

    init {
        this.ganks = ganks
    }

    override fun getItemCount(): Int {
        return (ganks?.size ?: 0) + 1
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, p1: Int) {

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