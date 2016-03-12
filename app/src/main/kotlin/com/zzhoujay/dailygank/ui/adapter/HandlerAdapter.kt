package com.zzhoujay.dailygank.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zzhoujay.dailygank.R
import kotlinx.android.synthetic.main.item_handler.view.*

/**
 * Created by zhou on 16-3-12.
 */
class HandlerAdapter(val context: Context, val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup?, type: Int): RecyclerView.ViewHolder? {
        if (type == type_handler) {
            return Holder(LayoutInflater.from(context).inflate(R.layout.item_handler, p0, false))
        }
        return adapter.onCreateViewHolder(p0, type)
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder?, position: Int) {
        if (position >= 1) {
            adapter.onBindViewHolder(p0, position)
        }
    }

    override fun getItemCount(): Int {
        return adapter.itemCount + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return type_handler
        }
        return super.getItemViewType(position)
    }

    class Holder(val root: View) : RecyclerView.ViewHolder(root) {
        val title: TextView

        init {
            title = root.handler_title
        }
    }


    companion object {
        const val type_handler = 0x1234
    }
}